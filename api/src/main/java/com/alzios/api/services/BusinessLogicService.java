package com.alzios.api.services;

import com.alzios.api.domain.*;
import com.alzios.api.domain.embeddedIds.UserExerciseDataId;
import com.alzios.api.dtos.*;
import com.alzios.api.exceptions.ResourceNotFoundException;
import com.alzios.api.logic.ProgramLogic;
import com.alzios.api.logic.TrainingComponentLogic;
import com.alzios.api.logic.TrainingLogic;
import com.alzios.api.repositories.*;
import com.alzios.api.utils.HungarianAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BusinessLogicService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ProgramRepository programRepository;

    @Autowired
    SerieRepository serieRepository;

    @Autowired
    TrainingRepository trainingRepository;

    @Autowired
    UserExerciseDataRepository userExerciseDataRepository;

    @Autowired
    TrainingComponentRepository trainingComponentRepository;

    @Autowired
    ExerciseRepository exerciseRepository;
    /**
     * Interface with api for create user's training
     *
     * @param userId     the user
     */
    public void createTraining(Long userId) {
        createOrUpdateTraining(userId, new ProgramLogic());
    }

    /**
     * Interface with the api  for update user's training.
     *
     * @param userId     the user
     * @param programDto actual training
     */
    public void updateTraining(Long userId, ProgramDto programDto) {
        ProgramLogic userProgram = new ProgramLogic();
        for(TrainingDto trainingDto : programDto.getTrainings()) {
            Optional<Training> training = trainingRepository.findById(trainingDto.getId());
            TrainingLogic trainingLogic = null;
            if(training.isPresent()) {
                trainingLogic=  new TrainingLogic(training.get());
            }
            for(TrainingComponentDto trainingComponentDto : trainingDto.getTrainingComponents()) {
                assert trainingLogic != null;
                for(TrainingComponent trainingComponent : training.get().getTrainingComponents()){
                    if(trainingComponent.getTrainingComponentId().getLayout().equals(trainingComponentDto.getLayout())) {
                        TrainingComponentLogic trainingComponentLogic = new TrainingComponentLogic(trainingComponent);

                        for(ExerciseDto exerciseDto : trainingComponentDto.getExercises()) {
                            Exercise exercise = exerciseRepository.findByName(exerciseDto.getName());
                            Optional<UserExerciseData> userExerciseDataOptional = userExerciseDataRepository.findByUserExerciseDataIdExerciseIdAndUserExerciseDataIdUserId(exercise.getId(), userId);
                            if(userExerciseDataOptional.isPresent()) {
                                trainingComponentLogic.getUserExerciseDataSet().add(userExerciseDataOptional.get());
                                if(trainingComponentDto.getExerciseChosen() != null){
                                    if(exerciseDto.getName().equals(trainingComponentDto.getExerciseChosen().getName())){
                                        trainingComponentLogic.setChosenExercise(userExerciseDataOptional.get());
                                    }
                                }

                            }
                        }
                        trainingLogic.getTrainingComponentLogicList().add(trainingComponentLogic);
                    }
                }
            }
            userProgram.getTrainingLogicList().add(trainingLogic);
        }
        createOrUpdateTraining(userId, userProgram);
    }
    /**
     *
     * Create or update the training.
     * <ul>
     * 	<li>1) First we fill (if it's not already the case) the structure of the training.</li>
     * 	<li>2) Then, we remove the training that the user had already done.</li>
     * 	<li>3) We put a mark on every exercises at every place in the structure.</li>
     * 	<li>4) We compute the hungarian algorithm it will find the best we the attribute for every exercises a place in the structure.</li>
     * 	<li>5) Construction of every series and saving in the database.
     * </ul>
     * <br>
     * The hungarian algorithm solves an assignment problem : <a href="https://en.wikipedia.org/wiki/Hungarian_algorithm">Wikipedia</a>
     * <br>
     *  For the case the some exercises are in superset : for the moment we considerate that we can only put two exercises in a
     *  super set. For that two training components with is_super_set = true will merge together.
     *
     * @param userId the user
     */
    private void createOrUpdateTraining(Long userId, ProgramLogic userProgram) {
        List<Exercise> exerciseList = new ArrayList<>();
        Optional<User> userRequest = userRepository.findById(userId);
        if(userRequest.isEmpty()){
            throw new ResourceNotFoundException("User with id " + userId + " not found;");
        }
        User user = userRequest.get();
        /*
         * 1) : we construct the user structure
         * if we can't find the data in the user we try to find it in from the database
         */
        serieRepository.deleteNotDoneByUserId(userId);

        if(userProgram.getTrainingLogicList().isEmpty()) {
            for (Training training:trainingRepository.findTrainingsByUserId(userId)) {
                TrainingLogic trainingLogic = new TrainingLogic(training);
                for(TrainingComponent trainingComponent : training.getTrainingComponents()) {
                    TrainingComponentLogic trainingComponentLogic = new TrainingComponentLogic(trainingComponent);
                    for (Exercise exercise: trainingComponent.getTrainingComponentId().getExerciseType().getExercises()) {
                        Optional<UserExerciseData> userExerciseDataRequest = userExerciseDataRepository.findByUserExerciseDataIdExerciseIdAndUserExerciseDataIdUserId(exercise.getId(), userId);
                        UserExerciseData userExerciseData = null;
                        if(userExerciseDataRequest.isPresent()){
                            userExerciseData = userExerciseDataRequest.get();
                        } else {
                            UserExerciseData newUserExerciseData = new UserExerciseData();
                            newUserExerciseData.setUserExerciseDataId(new UserExerciseDataId(exercise, user));
                            userExerciseDataRepository.save(newUserExerciseData);
                            userExerciseData = newUserExerciseData;
                        }
                        trainingComponentLogic.getUserExerciseDataSet().add(userExerciseData);
                        if(!exerciseList.contains(exercise)){ exerciseList.add(exercise); }
                    }

                    trainingLogic.getTrainingComponentDtoList().add(trainingComponentLogic);
                }
                Collections.sort(trainingLogic.getTrainingComponentDtoList());
                userProgram.getTrainingLogicList().add(trainingLogic);
            }
            Collections.sort(userProgram.getTrainingLogicList());
        } else {
            for (TrainingLogic trainingLogic: userProgram.getTrainingLogicList()) {
                for(TrainingComponentLogic trainingComponentLogic : trainingLogic.getTrainingComponentLogicList()) {
                    for (UserExerciseData userExerciseData: trainingComponentLogic.getUserExerciseDataSet()) {
                        trainingComponentLogic.getUserExerciseDataSet().add(userExerciseData);
                        if(!exerciseList.contains(userExerciseData.getUserExerciseDataId().getExercise())){ exerciseList.add(userExerciseData.getUserExerciseDataId().getExercise()); }
                    }

                    //trainingLogic.getTrainingComponentDtoList().add(trainingComponentLogic);
                }
                Collections.sort(trainingLogic.getTrainingComponentDtoList());
                //userProgram.getTrainingLogicList().add(trainingLogic);
            }
            Collections.sort(userProgram.getTrainingLogicList());
        }


        /*
         * 2) : Removing the trainings that the user already did.
         */

        // Search biomenic functions that the user did
        Map<Integer,Set<BiomecanicFunction>> biomecanicFunctionListMap = new HashMap<>();
        for(Serie s:serieRepository.findActualWeekAndDoneByUserId(userId)) {
            int layout = s.getTrainingComponent().getTrainingComponentId().getLayout();
            List<BiomecanicFunction> biomecanicFunctionsInSerie = s.getTrainingComponent().getTrainingComponentId().getBiomecanicFunctionList().getBiomecanicFunctions();
            if(!biomecanicFunctionListMap.containsKey(layout)){
                Set<BiomecanicFunction> biomecanicFunctionSet = new HashSet<>(biomecanicFunctionsInSerie);
                biomecanicFunctionListMap.put(layout, biomecanicFunctionSet);
            } else {
                biomecanicFunctionListMap.get(layout).addAll(biomecanicFunctionsInSerie);
            }
        }

        int nbTrainingToDelete = userProgram.getTrainingLogicList().size() - biomecanicFunctionListMap.size();

        /*
         * for every biomecanic function list we find the training which correspond
         * to the list of biomecanic function that the user has done.
         */
        for (Integer layout: biomecanicFunctionListMap.keySet()) {
            if(nbTrainingToDelete>0){
                double min = Double.POSITIVE_INFINITY;
                TrainingLogic t_done = null;
                for(TrainingLogic training : userProgram.getTrainingLogicList()) {

                    // Get every biomecanic function from training in actual program
                    Set<BiomecanicFunction> bf_training = new HashSet<>();
                    for(TrainingComponentLogic trainingComponent :training.getTrainingComponentDtoList()){
                        bf_training.addAll(trainingComponent.getTrainingComponent().getTrainingComponentId().getBiomecanicFunctionList().getBiomecanicFunctions());
                    }

                    Set<BiomecanicFunction> bf_training_and_done = new HashSet<>(biomecanicFunctionListMap.get(layout));
                    bf_training_and_done.addAll(bf_training);

                    // The cost is how mush the list of biomecanic function worked is far of the training that
                    // The user has to do the training tested
                    double cost = 1.0 * bf_training_and_done.size() / (bf_training.size() + biomecanicFunctionListMap.get(layout).size() - bf_training_and_done.size());

                    if(cost<min) {
                        min = cost;
                        t_done = training;
                    }
                }
                userProgram.getTrainingLogicList().remove(t_done);
                nbTrainingToDelete--;
            }
        }

        //mapEl correspond to a list of exercise with the same index every time we have to use them
        Map<Exercise, Integer> exerciseIntegerMap = new HashMap<>();
        int exerciseIntegerMapSize = exerciseList.size();
        for (int i = 0; i < exerciseIntegerMapSize; i++) {
            exerciseIntegerMap.put(exerciseList.get(i), i);
        }

        /*
         * 3) We put a mark on every exercise for every place in the program
         */
        int[][] tabExerciseTrainingComponent = new int[exerciseIntegerMapSize][exerciseIntegerMapSize];
        int k = 0;

        Morphology userMorphology = user.getMorphology();

        // Get nb exercise done.
        Integer nbDoneExerciseUser = 0;
        for(TrainingLogic trainingLogic : userProgram.getTrainingLogicList()){
            for(TrainingComponentLogic trainingComponentLogic : trainingLogic.getTrainingComponentDtoList()) {
                for (UserExerciseData userExerciseData : trainingComponentLogic.getUserExerciseDataSet()) {
                    nbDoneExerciseUser += userExerciseData.getNbDone();
                }
            }
        }

        for(TrainingLogic trainingLogic : userProgram.getTrainingLogicList()) {
            for (TrainingComponentLogic trainingComponentLogic : trainingLogic.getTrainingComponentDtoList()) {
                double mark = 0.0;
                int nbDone = 0;
                List<BiomecanicFunction> biomecanicFunctions_exercise = null;
                List<BiomecanicFunction> biomecanicFunctions_trainingComponent = trainingComponentLogic.getTrainingComponent().getTrainingComponentId().getBiomecanicFunctionList().getBiomecanicFunctions();

                if(trainingComponentLogic.getChosenExercise() == null){
                    for(UserExerciseData userExerciseData : trainingComponentLogic.getUserExerciseDataSet()) {

                        mark = userExerciseData.getMark();
                        nbDone = userExerciseData.getNbDone();

                        double cost_nbDone = (nbDone+1) * 1.0 / (nbDoneExerciseUser+1);

                        // Biomecanic function cost
                        biomecanicFunctions_exercise = userExerciseData.getUserExerciseDataId().getExercise().getBiomecanicFunctions();
                        Set<BiomecanicFunction> biomecanicFunctionSet = new HashSet<>(biomecanicFunctions_exercise);
                        biomecanicFunctionSet.retainAll(biomecanicFunctions_trainingComponent);

                        int card_biomecanicFunctions_exercise = biomecanicFunctions_exercise.size();
                        int card_biomecanicFunctions_trainingComponent = biomecanicFunctions_trainingComponent.size();
                        int card_biomecanicFunctions_exercise_trainingComponent = biomecanicFunctionSet.size();


                        double cost_biomecanicFunctions = 2.0 * card_biomecanicFunctions_exercise_trainingComponent/ (card_biomecanicFunctions_exercise + card_biomecanicFunctions_trainingComponent);
                        // Morphology cost
                        Set<Morphology> morphologySet = new HashSet<>(userExerciseData.getUserExerciseDataId().getExercise().getMorphologies());
                        morphologySet.retainAll(new ArrayList<>(Arrays.asList(user.getMorphology())));

                        int card_morphology_user = 1;
                        int card_morphologies_exercise = userExerciseData.getUserExerciseDataId().getExercise().getMorphologies().size();
                        int card_morphologies_exercise_user = morphologySet.size();


                        double cost_morphology = 1.0;
                        if(card_morphologies_exercise_user == 0) {
                            cost_morphology = 0.0001;
                        }

                        // Equipment cost
                        Set<Equipment> equipmentSet = new HashSet<>(userExerciseData.getUserExerciseDataId().getExercise().getEquipments());
                        equipmentSet.retainAll(user.getEquipments());

                        int card_equipments_user = user.getEquipments().size();
                        int card_equipments_exercise = userExerciseData.getUserExerciseDataId().getExercise().getEquipments().size();
                        int card_equipments_exercise_user = equipmentSet.size();

                        double cost_equipments = 1.0;

                        if(0 == card_equipments_exercise_user && card_equipments_exercise !=0){
                            cost_equipments = 0.0001;
                        }

                        int value = (int) Math.floor(1000000.0 * mark * cost_morphology * cost_biomecanicFunctions *cost_equipments  / cost_nbDone );
                        tabExerciseTrainingComponent[exerciseIntegerMap.get(userExerciseData.getUserExerciseDataId().getExercise())][k] = value;
                    }
                } else {
                    tabExerciseTrainingComponent[exerciseIntegerMap.get(trainingComponentLogic.getChosenExercise().getUserExerciseDataId().getExercise())][k] = Integer.MAX_VALUE;
                }
                k++;
            }
        }

        /*
         * 4) We use the hungarian algorithm to find the result
         * The hungarian algorithm consist in solving an assignation problem
         */

        // We fill the rest of the box with 0
        int i;
        /*while (k < exerciseIntegerMapSize) {
            for (i = 0; i < exerciseIntegerMapSize; i++) {
                tabExerciseTrainingComponent[i][k] = 0;
            }
            k++;
        }*/

        // We find the max of the tab
        int max = 0;
        for (i = 0; i < exerciseIntegerMapSize; i++) {
            for (int j = 0; j < exerciseIntegerMapSize; j++) {
                if (max < tabExerciseTrainingComponent[i][j]) {
                    max = tabExerciseTrainingComponent[i][j];
                }
            }
        }

        // For every box we compute the max - the box value
        // Here we want to make a min problem because the hungarian algorithm work with min value
        for (i = 0; i < exerciseIntegerMapSize; i++) {
            for (int j = 0; j < exerciseIntegerMapSize; j++) {
                tabExerciseTrainingComponent[i][j] = max - tabExerciseTrainingComponent[i][j];
            }
        }

        // We execute the algorithm
        HungarianAlgorithm ha = new HungarianAlgorithm(tabExerciseTrainingComponent);
        int[][] assignment = ha.findOptimalAssignment();

        /*
         * 5) We construct the series the user will have to do with the result of the algorithm.
         */

        // First we chose the exercise from the result of the algorithm.
        i = 0;
        for (TrainingLogic trainingLogic : userProgram.getTrainingLogicList()) {
            for (TrainingComponentLogic trainingComponentLogic : trainingLogic.getTrainingComponentDtoList()) {
                Exercise exercise = exerciseList.get(assignment[i][1]);
                Optional<UserExerciseData> userExerciseDataRequest = userExerciseDataRepository.findByUserExerciseDataIdExerciseIdAndUserExerciseDataIdUserId(exercise.getId(), userId);
                if(userExerciseDataRequest.isPresent()) {
                    trainingComponentLogic.setChosenExercise(userExerciseDataRequest.get());
                } else {
                    UserExerciseData newUserExerciseData = new UserExerciseData();
                    newUserExerciseData.setUserExerciseDataId(new UserExerciseDataId(exercise, user));
                    trainingComponentLogic.setChosenExercise(newUserExerciseData);
                }
                i++;
            }
        }

        // Then we build our series
        for(TrainingLogic trainingLogic : userProgram.getTrainingLogicList()) {
            /*
             * For every component we construct the series
             * and if it's a super set.
             */
            Iterator<TrainingComponentLogic> trainingComponentDtoIterator = trainingLogic.getTrainingComponentDtoList().iterator();

            TrainingComponentLogic trainingComponentLogic = null;

            int index = 0;

            // if the component is the second component
            boolean is_last_super_set = false;

            while(trainingComponentDtoIterator.hasNext()){

                if(!is_last_super_set) {
                    trainingComponentLogic = trainingComponentDtoIterator.next();
                    index++;
                }

                List<TrainingComponentLogic> trainingComponentDtos_superSet = new ArrayList<>();
                int previous_layout = trainingComponentLogic.getTrainingComponent().getTrainingComponentId().getLayout();

                do{
                    trainingComponentDtos_superSet.add(trainingComponentLogic);
                    if(trainingComponentDtoIterator.hasNext() && trainingComponentLogic.getTrainingComponent().getIsSuperSet()) {
                        TrainingComponentLogic trainingComponentLogicSuperSet = trainingLogic.getTrainingComponentDtoList().get(index);

                        // if the super set component are following there self in the training
                        // So the next component is the last super set.

                        if(trainingComponentLogicSuperSet.getTrainingComponent().getIsSuperSet() && Math.abs(previous_layout - trainingComponentLogicSuperSet.getTrainingComponent().getTrainingComponentId().getLayout()) == 1) {
                            trainingComponentLogic = trainingComponentDtoIterator.next();
                            index++;

                            is_last_super_set = true;
                        } else {
                            is_last_super_set = false;
                        }
                    } else {
                        is_last_super_set = false;
                    }
                } while (trainingComponentDtoIterator.hasNext() && is_last_super_set);

                TrainingMethod trainingMethod = trainingComponentLogic.getTrainingComponent().getTrainingComponentId().getTrainingMethod();

                int it = 0;

                // We construct every serie from the repartition from the data base.
                for(SerieDivision serieDivision : trainingComponentLogic.getTrainingComponent().getTrainingComponentId().getTrainingMethod().getSerieDivisions()) {
                    TrainingComponentLogic trainingComponentLogicSerie = trainingComponentDtos_superSet.get(it);
                    UserExerciseData userExerciseData = trainingComponentLogicSerie.getChosenExercise();
                    Serie serie = new Serie();
                    serie.setExpectedRepetitions(serieDivision.getNbRep());
                    serie.setRepetitions(serieDivision.getNbRep());
                    serie.setExpectedWeight(serieDivision.getWeight() * userExerciseData.getWeight() / 100);
                    serie.setWeight(serieDivision.getWeight() * userExerciseData.getWeight() / 100);
                    serie.setLayout(serieDivision.getLayout());
                    serie.setInActualWeek(true);
                    serie.setRestDuration(serieDivision.getRestDuration());

                    serie.setTrainingComponent(trainingComponentLogicSerie.getTrainingComponent());
                    serie.setExercise(userExerciseData.getUserExerciseDataId().getExercise());
                    serie.setUser(user);

                    serieRepository.save(serie);

                    it = (it + 1) % trainingComponentDtos_superSet.size();
                }
            }
        }

    }

    /**
     * Interface with the api without super set.
     *
     * @param userId     the user
     * @return the user training
     */
    public ProgramDto getTrainingByUserId(Long userId) {
        List<Serie> series = serieRepository.findNextTrainingByUserId(userId);
        if(series.isEmpty()) {
            throw new ResourceNotFoundException("No training found for the user with id " + userId);
        }
        return getTraining(series);
    }

    /**
     * Get the user's training without the concept of superset.
     *
     * @param series     the series
     * @return the user training
     */
    private ProgramDto getTraining(List<Serie> series) {
        ProgramDto programDto = new ProgramDto();
        programDto.setTrainings(new ArrayList<>());

        for(Serie serie : series) {
            // We fill the training Map with every training if it's not already contained in.
            boolean inTrainingMap = false;
            TrainingDto trainingDto = null;
            for (TrainingDto trainingDtoIter : programDto.getTrainings()) {

                if(trainingDtoIter.getLayout().equals(serie.getTrainingComponent().getTrainingComponentId().getTraining().getLayout())) {
                    inTrainingMap = true;
                    trainingDto = trainingDtoIter;
                }

            }
            if(!inTrainingMap) {
                Training training = serie.getTrainingComponent().getTrainingComponentId().getTraining();

                trainingDto = new TrainingDto();
                trainingDto.setDuration(training.getDuration());
                trainingDto.setLayout(training.getLayout());
                trainingDto.setTrainingComponents(new ArrayList<>());
                trainingDto.setId(training.getId());
                programDto.getTrainings().add(trainingDto);
            }

            // Same for the component
            boolean inTrainingComponentMap = false;
            TrainingComponentDto trainingComponentDto = null;
            for (TrainingComponentDto trainingComponentDtoIter : trainingDto.getTrainingComponents()) {
                if(trainingComponentDtoIter.getLayout().equals(serie.getTrainingComponent().getTrainingComponentId().getLayout())){
                    inTrainingComponentMap = true;
                    trainingComponentDto = trainingComponentDtoIter;
                    SerieDto serieDto = new SerieDto();
                    serieDto.setExpectedWeight(serie.getExpectedWeight());
                    serieDto.setExpectedRep(serie.getExpectedRepetitions());
                    trainingComponentDto.getSeries().add(serieDto);
                }
            }

            if(!inTrainingComponentMap) {
                trainingComponentDto = new TrainingComponentDto();
                //trainingComponentDto.setTrainingMethod(serie.getTrainingComponent().getTrainingComponentId().getTrainingMethod());
                trainingComponentDto.setLayout(serie.getTrainingComponent().getTrainingComponentId().getLayout());
                trainingComponentDto.setSeries(new ArrayList<>());

                SerieDto serieDto = new SerieDto();
                serieDto.setExpectedRep(serie.getExpectedRepetitions());
                serieDto.setExpectedWeight(serie.getExpectedWeight());
                trainingComponentDto.getSeries().add(serieDto);
                List<ExerciseDto> exerciseDtos = new ArrayList<>();
                for(Exercise exercise :serie.getTrainingComponent().getTrainingComponentId().getExerciseType().getExercises()) {
                    ExerciseDto exerciseDto = new ExerciseDto();
                    exerciseDto.setName(exercise.getName());
                    exerciseDto.setDescription(exercise.getDescription());
                    exerciseDtos.add(exerciseDto);
                }
                trainingComponentDto.setExercises(exerciseDtos);
                trainingDto.getTrainingComponents().add(trainingComponentDto);
            }

            Exercise exerciseChosen = serie.getExercise();
            Optional<UserExerciseData> userExerciseDataExerciseChosen = userExerciseDataRepository.findByUserExerciseDataIdExerciseIdAndUserExerciseDataIdUserId(exerciseChosen.getId(), serie.getUser().getId());

            if(userExerciseDataExerciseChosen.isPresent()){
                UserExerciseData userExerciseData = userExerciseDataExerciseChosen.get();
                ExerciseDto exerciseDto = new ExerciseDto();
                Exercise exercise = userExerciseData.getUserExerciseDataId().getExercise();
                exerciseDto.setName(exercise.getName());
                exerciseDto.setDescription(exercise.getDescription());
                trainingComponentDto.setExerciseChosen(exerciseDto);
                UserExerciseDataDto userExerciseDataDto = new UserExerciseDataDto();
                userExerciseDataDto.setMark(userExerciseData.getMark());
                userExerciseDataDto.setNbDone(userExerciseData.getNbDone());
                userExerciseDataDto.setWeight(userExerciseData.getWeight());
                trainingComponentDto.setData(userExerciseDataDto);
            }

            boolean inExerciseList = false;
            Optional<ExerciseDto> exerciseDto;
            for(ExerciseDto exerciseDtoIter : trainingComponentDto.getExercises()){
                if(exerciseDtoIter.getName().equals(serie.getExercise().getName())) {
                    inExerciseList = true;
                    exerciseDto = Optional.of(exerciseDtoIter);
                }
            }

            if(!inExerciseList) {
                Exercise exercise = serie.getExercise();
                ExerciseDto exerciseDtoTemp = new ExerciseDto();
                exerciseDtoTemp.setName(exercise.getName());
                exerciseDtoTemp.setDescription(exercise.getDescription());

                exerciseDto = Optional.of(exerciseDtoTemp);
                trainingComponentDto.getExercises().add(exerciseDto.get());
            }
        }

        // Sorting
        for(TrainingDto trainingDto : programDto.getTrainings()) {
            Collections.sort(trainingDto.getTrainingComponents());
        }
        Collections.sort(programDto.getTrainings());
        return programDto;
    }

    /**
     * Interface with the api for superset training
     *
     * @param userId     the user
     * @return the user training
     */
    public ProgramLogic getTrainingSuperSetByUserId(Long userId) {
        List<Serie> series = serieRepository.findNextTrainingByUserId(userId);
        if(series.isEmpty()) {
            throw new ResourceNotFoundException("No training found for the user with id " + userId);
        }
        return getTrainingSuperSet(series);
    }

    /**
     * Get the user's training with the concept of superset.
     *
     * @param series     the series
     * @return the user training
     */
    private ProgramLogic getTrainingSuperSet(List<Serie> series) {
        ProgramLogic programLogic = new ProgramLogic();
        programLogic.setTrainingLogicList(new ArrayList<>());

        for(Serie serie : series) {
            // We fill the training Map with every training if it's not already contained in.
            boolean inTrainingMap = false;
            TrainingLogic trainingLogic = null;
            for (TrainingLogic trainingLogicIter : programLogic.getTrainingLogicList()) {
                inTrainingMap = true;
                trainingLogic = trainingLogicIter;
            }
            if(!inTrainingMap) {
                trainingLogic = new TrainingLogic(serie.getTrainingComponent().getTrainingComponentId().getTraining());
                trainingLogic.setTrainingComponentDtoList(new ArrayList<>());
                programLogic.getTrainingLogicList().add(trainingLogic);
            }

            // Same for the component
            boolean inTrainingComponentMap = false;
            TrainingComponentLogic trainingComponentLogic = null;

            for (TrainingComponentLogic trainingComponentLogicIter : trainingLogic.getTrainingComponentDtoList()) {
                if(trainingComponentLogicIter.getTrainingComponent().getTrainingComponentId().getLayout() == serie.getTrainingComponent().getTrainingComponentId().getLayout()){
                    inTrainingComponentMap = true;
                    trainingComponentLogic = trainingComponentLogicIter;
                    trainingComponentLogic.getSeries().add(serie);
                } else {
                    assert trainingComponentLogic != null;
                    if (trainingComponentLogic.getTrainingComponent().getIsSuperSet() && Math.abs(trainingComponentLogic.getTrainingComponent().getTrainingComponentId().getLayout() - serie.getLayout()) <=1) {
                        // If the training components are super set and following in the training. We say that the
                        // serie among to the training component of the first exercise.
                        TrainingComponentLogic trainingComponentLogicSuperset = new TrainingComponentLogic(serie.getTrainingComponent());

                        if(trainingComponentLogicSuperset.getTrainingComponent().getIsSuperSet()) {
                            inTrainingComponentMap = true;
                            trainingComponentLogic = trainingComponentLogicSuperset;
                            trainingComponentLogic.getSeries().add(serie);
                        }
                    }
                }
            }

            if(!inTrainingComponentMap) {
                trainingComponentLogic = new TrainingComponentLogic(serie.getTrainingComponent());
                trainingComponentLogic.setSeries(new ArrayList<>());

                trainingComponentLogic.getSeries().add(serie);
                trainingComponentLogic.setUserExerciseDataSet(new HashSet<>());

                // TODO if error here my friend
                //trainingComponentDto.setUserExerciseDataSet(new HashSet<>(userExerciseDataRepository.findByUserExerciseDataIdUserId(serie.getUser().getId())));

                trainingLogic.getTrainingComponentDtoList().add(trainingComponentLogic);
            }

            Exercise exerciseChosen = serie.getExercise();
            Optional<UserExerciseData> userExerciseDataExerciseChosen = userExerciseDataRepository.findByUserExerciseDataIdExerciseIdAndUserExerciseDataIdUserId(exerciseChosen.getId(), serie.getUser().getId());

            if(userExerciseDataExerciseChosen.isPresent()){
                trainingComponentLogic.setChosenExercise(userExerciseDataExerciseChosen.get());
            }

            boolean inExerciseList = false;
            Optional<UserExerciseData> userExerciseData;
            for(UserExerciseData userExerciseDataIter : trainingComponentLogic.getUserExerciseDataSet()){
                if(userExerciseDataIter.getUserExerciseDataId().getExercise().equals(serie.getExercise())) {
                    inExerciseList = true;
                    userExerciseData = Optional.of(userExerciseDataIter);
                }
            }

            if(!inExerciseList) {
                userExerciseData = userExerciseDataRepository.findByUserExerciseDataIdExerciseIdAndUserExerciseDataIdUserId(serie.getExercise().getId(), serie.getUser().getId());
                if(userExerciseData.isPresent()){
                    trainingComponentLogic.getUserExerciseDataSet().add(userExerciseData.get());
                }
            }
        }
        return programLogic;
    }


}
