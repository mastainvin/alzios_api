package com.alzios.api.test;

import com.alzios.api.domain.*;
import com.alzios.api.domain.embeddedIds.TrainingComponentId;
import com.alzios.api.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Service
public class FillDatabase {
    @Autowired
    AvailabilityRepository availabilityRepository;

    @Autowired
    BiomecanicFunctionListRepository biomecanicFunctionListRepository;

    @Autowired
    BiomecanicFunctionRepository biomecanicFunctionRepository;

    @Autowired
    BodyLimbRepository bodyLimbRepository;

    @Autowired
    EquipmentRepository equipmentRepository;

    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    ExerciseTypeRepository exerciseTypeRepository;

    @Autowired
    FileRepository fileRepository;

    @Autowired
    GoalRepository goalRepository;

    @Autowired
    MorphologyRepository morphologyRepository;

    @Autowired
    ProgramRepository programRepository;

    @Autowired
    SerieDivisionRepository serieDivisionRepository;

    @Autowired
    SerieRepository serieRepository;

    @Autowired
    TrainingComponentRepository trainingComponentRepository;

    @Autowired
    TrainingMethodRepository trainingMethodRepository;

    @Autowired
    TrainingRepository trainingRepository;

    @Autowired
    TrainingTypeRepository trainingTypeRepository;

    @Autowired
    UserExerciseDataRepository exerciseDataRepository;

    @Autowired
    UserRepository userRepository;


    public void basic() {
        // Body limbs
        BodyLimb pectoraux = new BodyLimb();
        pectoraux.setName("pectoraux");
        bodyLimbRepository.save(pectoraux);
        BodyLimb dos = new BodyLimb();
        dos.setName("dos");
        bodyLimbRepository.save(dos);
        BodyLimb biceps = new BodyLimb();
        biceps.setName("biceps");
        bodyLimbRepository.save(biceps);
        BodyLimb triceps = new BodyLimb();
        triceps.setName("triceps");
        bodyLimbRepository.save(triceps);
        BodyLimb epaules = new BodyLimb();
        epaules.setName("épaules");
        bodyLimbRepository.save(epaules);
        BodyLimb trapezes = new BodyLimb();
        trapezes.setName("trapèzes");
        bodyLimbRepository.save(trapezes);
        BodyLimb lombaires = new BodyLimb();
        lombaires.setName("lombaires");
        bodyLimbRepository.save(lombaires);
        BodyLimb quadriceps = new BodyLimb();
        quadriceps.setName("quadriceps");
        bodyLimbRepository.save(quadriceps);
        BodyLimb ischiojambiers = new BodyLimb();
        ischiojambiers.setName("ischio-jambiers");
        bodyLimbRepository.save(ischiojambiers);
        BodyLimb adducteurs = new BodyLimb();
        adducteurs.setName("adducteurs");
        bodyLimbRepository.save(adducteurs);
        BodyLimb abducteurs = new BodyLimb();
        abducteurs.setName("abducteurs");
        bodyLimbRepository.save(abducteurs);
        BodyLimb mollets = new BodyLimb();
        mollets.setName("mollets");
        bodyLimbRepository.save(mollets);
        BodyLimb coudes = new BodyLimb();
        coudes.setName("coudes");
        bodyLimbRepository.save(coudes);
        BodyLimb cou = new BodyLimb();
        cou.setName("cou");
        bodyLimbRepository.save(cou);
        BodyLimb chevilles = new BodyLimb();
        chevilles.setName("chevilles");
        bodyLimbRepository.save(chevilles);
        BodyLimb clavicule = new BodyLimb();
        clavicule.setName("clavicule");
        bodyLimbRepository.save(clavicule);
        BodyLimb poignets = new BodyLimb();
        poignets.setName("poignets");
        bodyLimbRepository.save(poignets);
        BodyLimb genoux = new BodyLimb();
        genoux.setName("genoux");
        bodyLimbRepository.save(genoux);
        BodyLimb hanches = new BodyLimb();
        hanches.setName("hanches");
        bodyLimbRepository.save(hanches);
        BodyLimb fessier = new BodyLimb();
        fessier.setName("fessier");
        bodyLimbRepository.save(fessier);
        BodyLimb abdominaux = new BodyLimb();
        abdominaux.setName("abdominaux");
        bodyLimbRepository.save(abdominaux);


        // Equipements
        Equipment halteres = new Equipment();
        halteres.setName("haltères");
        equipmentRepository.save(halteres);
        Equipment poidslibres = new Equipment();
        poidslibres.setName("poids libres");
        equipmentRepository.save(poidslibres);
        Equipment elastiques = new Equipment();
        elastiques.setName("élastiques");
        equipmentRepository.save(elastiques);
        Equipment machines = new Equipment();
        machines.setName("machines");
        equipmentRepository.save(machines);
        Equipment parcdestreetworkout = new Equipment();
        parcdestreetworkout.setName("parc de street workout");
        equipmentRepository.save(parcdestreetworkout);

        // Biomecanic functions
        BiomecanicFunction chevilleExtensionFlexion = new BiomecanicFunction();
        chevilleExtensionFlexion.setName("chevilleExtensionFlexion");
        chevilleExtensionFlexion.setBodyLimbs(new ArrayList<>(Arrays.asList(mollets)));
        biomecanicFunctionRepository.save(chevilleExtensionFlexion);
        BiomecanicFunction hanchesExtension = new BiomecanicFunction();
        hanchesExtension.setName("hanchesExtension");
        hanchesExtension.setBodyLimbs(new ArrayList<>(Arrays.asList(lombaires, ischiojambiers, fessier)));
        biomecanicFunctionRepository.save(hanchesExtension);
        BiomecanicFunction hanchesFlexion = new BiomecanicFunction();
        hanchesFlexion.setName("hanchesFlexion");
        hanchesFlexion.setBodyLimbs(new ArrayList<>(Arrays.asList(quadriceps, abdominaux)));
        biomecanicFunctionRepository.save(hanchesFlexion);
        BiomecanicFunction brasAbduction = new BiomecanicFunction();
        brasAbduction.setName("brasAbduction");
        brasAbduction.setBodyLimbs(new ArrayList<>(Arrays.asList(dos, epaules)));
        biomecanicFunctionRepository.save(brasAbduction);
        BiomecanicFunction brasAdduction = new BiomecanicFunction();
        brasAdduction.setName("brasAdduction");
        brasAdduction.setBodyLimbs(new ArrayList<>(Arrays.asList(pectoraux, epaules)));
        BiomecanicFunction brasAntepulsion = new BiomecanicFunction();
        brasAntepulsion.setName("brasAntepulsion");
        brasAntepulsion.setBodyLimbs(new ArrayList<>(Arrays.asList(epaules)));
        biomecanicFunctionRepository.save(brasAntepulsion);
        BiomecanicFunction brasRetropulsion = new BiomecanicFunction();
        brasRetropulsion.setName("brasRetropulsion");
        brasRetropulsion.setBodyLimbs(new ArrayList<>(Arrays.asList(pectoraux, dos, triceps, epaules)));
        biomecanicFunctionRepository.save(brasRetropulsion);
        BiomecanicFunction coudeFlexion = new BiomecanicFunction();
        coudeFlexion.setName("coudeFlexion");
        coudeFlexion.setBodyLimbs(new ArrayList<>(Arrays.asList(biceps)));
        biomecanicFunctionRepository.save(coudeFlexion);
        BiomecanicFunction coudeExtension = new BiomecanicFunction();
        coudeExtension.setName("coudeExtension");
        coudeExtension.setBodyLimbs(new ArrayList<>(Arrays.asList(triceps)));
        biomecanicFunctionRepository.save(coudeExtension);
        BiomecanicFunction jambeFlexion = new BiomecanicFunction();
        jambeFlexion.setName("jambeFlexion");
        jambeFlexion.setBodyLimbs(new ArrayList<>(Arrays.asList(ischiojambiers, fessier)));
        biomecanicFunctionRepository.save(jambeFlexion);
        BiomecanicFunction jambeExtension = new BiomecanicFunction();
        jambeExtension.setName("jambeExtension");
        jambeExtension.setBodyLimbs(new ArrayList<>(Arrays.asList(quadriceps)));
        biomecanicFunctionRepository.save(jambeExtension);
        BiomecanicFunction jambeAdduction = new BiomecanicFunction();
        jambeAdduction.setName("jambeAdduction");
        jambeAdduction.setBodyLimbs(new ArrayList<>(Arrays.asList(adducteurs)));
        biomecanicFunctionRepository.save(jambeAdduction);
        BiomecanicFunction jambeAbduction = new BiomecanicFunction();
        jambeAbduction.setName("jambeAbduction");
        jambeAbduction.setBodyLimbs(new ArrayList<>(Arrays.asList(abducteurs, fessier)));
        biomecanicFunctionRepository.save(jambeAbduction);

        // Biomecanic functions lists
        BiomecanicFunctionList un = new BiomecanicFunctionList();
        un.setBiomecanicFunctions(new ArrayList<>(Arrays.asList(brasAbduction, coudeFlexion)));
        biomecanicFunctionListRepository.save(un);
        BiomecanicFunctionList deux = new BiomecanicFunctionList();
        deux.setBiomecanicFunctions(new ArrayList<>(Arrays.asList(brasAbduction, coudeFlexion)));
        biomecanicFunctionListRepository.save(deux);
        BiomecanicFunctionList trois = new BiomecanicFunctionList();
        trois.setBiomecanicFunctions(new ArrayList<>(Arrays.asList(hanchesFlexion)));
        biomecanicFunctionListRepository.save(trois);
        BiomecanicFunctionList quatre = new BiomecanicFunctionList();
        quatre.setBiomecanicFunctions(new ArrayList<>(Arrays.asList(chevilleExtensionFlexion, hanchesExtension, hanchesFlexion, jambeFlexion, jambeExtension, jambeAbduction, jambeAdduction)));
        biomecanicFunctionListRepository.save(quatre);
        BiomecanicFunctionList cinq = new BiomecanicFunctionList();
        cinq.setBiomecanicFunctions(new ArrayList<>(Arrays.asList(brasAdduction, coudeExtension)));
        biomecanicFunctionListRepository.save(cinq);
        BiomecanicFunctionList six = new BiomecanicFunctionList();
        six.setBiomecanicFunctions(new ArrayList<>(Arrays.asList(brasAbduction, coudeFlexion)));
        biomecanicFunctionListRepository.save(six);
        BiomecanicFunctionList neuf = new BiomecanicFunctionList();
        neuf.setBiomecanicFunctions(new ArrayList<>(Arrays.asList(hanchesExtension)));
        biomecanicFunctionListRepository.save(neuf);
        BiomecanicFunctionList dix = new BiomecanicFunctionList();
        dix.setBiomecanicFunctions(new ArrayList<>(Arrays.asList(brasAbduction, coudeFlexion)));
        biomecanicFunctionListRepository.save(dix);
        BiomecanicFunctionList onze = new BiomecanicFunctionList();
        onze.setBiomecanicFunctions(new ArrayList<>(Arrays.asList(hanchesFlexion, jambeExtension)));
        biomecanicFunctionListRepository.save(onze);

        //Morphologies
        Morphology longiligne = new Morphology();
        longiligne.setName("longiligne");
        morphologyRepository.save(longiligne);

        Morphology breviligne = new Morphology();
        breviligne.setName("breviligne");
        morphologyRepository.save(breviligne);

        //Objectifs
        Goal force = new Goal();
        force.setName("force");
        goalRepository.save(force);

        Goal remiseEnForme = new Goal();
        remiseEnForme.setName("remiseEnForme");
        goalRepository.save(remiseEnForme);

        Goal esthetisme = new Goal();
        esthetisme.setName("esthetisme");
        goalRepository.save(esthetisme);

        //Disponibilities
        Availability un120m = new Availability();
        un120m.setDuration(120);
        un120m.setLayout(1);
        availabilityRepository.save(un120m);

        Availability deux120m = new Availability();
        deux120m.setDuration(120);
        deux120m.setLayout(2);
        availabilityRepository.save(deux120m);

        Availability un90m = new Availability();
        un90m.setDuration(90);
        un90m.setLayout(1);
        availabilityRepository.save(un90m);

        Availability deux90m = new Availability();
        deux90m.setDuration(90);
        deux90m.setLayout(2);
        availabilityRepository.save(deux90m);

        // Serie division
        SerieDivision serieDiv1 = new SerieDivision();
        serieDiv1.setNbRep(3);
        serieDiv1.setWeight(90.0);
        serieDiv1.setLayout(1);
        serieDiv1.setRestDuration(180);
        serieDivisionRepository.save(serieDiv1);

        SerieDivision serieDiv2 = new SerieDivision();
        serieDiv2.setNbRep(3);
        serieDiv2.setWeight(90.0);
        serieDiv2.setLayout(2);
        serieDiv2.setRestDuration(180);
        serieDivisionRepository.save(serieDiv2);

        SerieDivision serieDiv3 = new SerieDivision();
        serieDiv3.setNbRep(3);
        serieDiv3.setWeight(90.0);
        serieDiv3.setLayout(3);
        serieDiv3.setRestDuration(120);
        serieDivisionRepository.save(serieDiv3);

        SerieDivision serieDiv4 = new SerieDivision();
        serieDiv4.setNbRep(4);
        serieDiv4.setWeight(87.0);
        serieDiv4.setLayout(1);
        serieDiv4.setRestDuration(180);
        serieDivisionRepository.save(serieDiv4);

        SerieDivision serieDiv5 = new SerieDivision();
        serieDiv5.setNbRep(4);
        serieDiv5.setWeight(87.0);
        serieDiv5.setLayout(2);
        serieDiv5.setRestDuration(180);
        serieDivisionRepository.save(serieDiv5);

        SerieDivision serieDiv6 = new SerieDivision();
        serieDiv6.setNbRep(4);
        serieDiv6.setWeight(87.0);
        serieDiv6.setLayout(3);
        serieDiv6.setRestDuration(180);
        serieDivisionRepository.save(serieDiv6);

        SerieDivision serieDiv7 = new SerieDivision();
        serieDiv7.setNbRep(4);
        serieDiv7.setWeight(84.0);
        serieDiv7.setLayout(4);
        serieDiv7.setRestDuration(120);
        serieDivisionRepository.save(serieDiv7);

        SerieDivision serieDiv8 = new SerieDivision();
        serieDiv8.setNbRep(5);
        serieDiv8.setWeight(85.0);
        serieDiv8.setLayout(1);
        serieDiv8.setRestDuration(180);
        serieDivisionRepository.save(serieDiv8);

        SerieDivision serieDiv9 = new SerieDivision();
        serieDiv9.setNbRep(5);
        serieDiv9.setWeight(85.0);
        serieDiv9.setLayout(2);
        serieDiv9.setRestDuration(180);
        serieDivisionRepository.save(serieDiv9);

        SerieDivision serieDiv10 = new SerieDivision();
        serieDiv10.setNbRep(5);
        serieDiv10.setWeight(85.0);
        serieDiv10.setLayout(3);
        serieDiv10.setRestDuration(180);
        serieDivisionRepository.save(serieDiv10);

        SerieDivision serieDiv11 = new SerieDivision();
        serieDiv11.setNbRep(5);
        serieDiv11.setWeight(85.0);
        serieDiv11.setLayout(4);
        serieDiv11.setRestDuration(180);
        serieDivisionRepository.save(serieDiv11);

        SerieDivision serieDiv12 = new SerieDivision();
        serieDiv12.setNbRep(5);
        serieDiv12.setWeight(85.0);
        serieDiv12.setLayout(5);
        serieDiv12.setRestDuration(120);
        serieDivisionRepository.save(serieDiv12);

        SerieDivision serieDiv13 = new SerieDivision();
        serieDiv13.setNbRep(6);
        serieDiv13.setWeight(85.0);
        serieDiv13.setLayout(1);
        serieDiv13.setRestDuration(0);
        serieDivisionRepository.save(serieDiv13);

        SerieDivision serieDiv14 = new SerieDivision();
        serieDiv14.setNbRep(10);
        serieDiv14.setWeight(50.0);
        serieDiv14.setLayout(2);
        serieDiv14.setRestDuration(150);
        serieDivisionRepository.save(serieDiv14);

        SerieDivision serieDiv15 = new SerieDivision();
        serieDiv15.setNbRep(6);
        serieDiv15.setWeight(85.0);
        serieDiv15.setLayout(3);
        serieDiv15.setRestDuration(0);
        serieDivisionRepository.save(serieDiv15);

        SerieDivision serieDiv16 = new SerieDivision();
        serieDiv16.setNbRep(10);
        serieDiv16.setWeight(50.0);
        serieDiv16.setLayout(4);
        serieDiv16.setRestDuration(150);
        serieDivisionRepository.save(serieDiv16);

        SerieDivision serieDiv17 = new SerieDivision();
        serieDiv17.setNbRep(6);
        serieDiv17.setWeight(85.0);
        serieDiv17.setLayout(5);
        serieDiv17.setRestDuration(0);
        serieDivisionRepository.save(serieDiv17);

        SerieDivision serieDiv18 = new SerieDivision();
        serieDiv18.setNbRep(10);
        serieDiv18.setWeight(50.0);
        serieDiv18.setLayout(6);
        serieDiv18.setRestDuration(150);
        serieDivisionRepository.save(serieDiv18);

        SerieDivision serieDiv19 = new SerieDivision();
        serieDiv19.setNbRep(6);
        serieDiv19.setWeight(85.0);
        serieDiv19.setLayout(7);
        serieDiv19.setRestDuration(0);
        serieDivisionRepository.save(serieDiv19);


        SerieDivision serieDiv20 = new SerieDivision();
        serieDiv20.setNbRep(10);
        serieDiv20.setWeight(50.0);
        serieDiv20.setLayout(8);
        serieDiv20.setRestDuration(120);
        serieDivisionRepository.save(serieDiv20);

        SerieDivision serieDiv21 = new SerieDivision();
        serieDiv21.setNbRep(10);
        serieDiv21.setWeight(75.0);
        serieDiv21.setLayout(1);
        serieDiv21.setRestDuration(120);
        serieDivisionRepository.save(serieDiv21);

        SerieDivision serieDiv22 = new SerieDivision();
        serieDiv22.setNbRep(10);
        serieDiv22.setWeight(75.0);
        serieDiv22.setLayout(2);
        serieDiv22.setRestDuration(120);
        serieDivisionRepository.save(serieDiv22);

        SerieDivision serieDiv23 = new SerieDivision();
        serieDiv23.setNbRep(10);
        serieDiv23.setWeight(75.0);
        serieDiv23.setLayout(3);
        serieDiv23.setRestDuration(120);
        serieDivisionRepository.save(serieDiv23);

        SerieDivision serieDiv24 = new SerieDivision();
        serieDiv24.setNbRep(10);
        serieDiv24.setWeight(75.0);
        serieDiv24.setLayout(4);
        serieDiv24.setRestDuration(120);
        serieDivisionRepository.save(serieDiv24);


        SerieDivision serieDiv25 = new SerieDivision();
        serieDiv25.setNbRep(10);
        serieDiv25.setWeight(75.0);
        serieDiv25.setLayout(1);
        serieDiv25.setRestDuration(150);
        serieDivisionRepository.save(serieDiv25);

        SerieDivision serieDiv26 = new SerieDivision();
        serieDiv26.setNbRep(10);
        serieDiv26.setWeight(75.0);
        serieDiv26.setLayout(2);
        serieDiv26.setRestDuration(150);
        serieDivisionRepository.save(serieDiv26);

        SerieDivision serieDiv27 = new SerieDivision();
        serieDiv27.setNbRep(10);
        serieDiv27.setWeight(75.0);
        serieDiv27.setLayout(3);
        serieDiv27.setRestDuration(150);
        serieDivisionRepository.save(serieDiv27);

        SerieDivision serieDiv28 = new SerieDivision();
        serieDiv28.setNbRep(10);
        serieDiv28.setWeight(75.0);
        serieDiv28.setLayout(4);
        serieDiv28.setRestDuration(120);
        serieDivisionRepository.save(serieDiv28);

        SerieDivision serieDiv29 = new SerieDivision();
        serieDiv29.setNbRep(15);
        serieDiv29.setWeight(60.0);
        serieDiv29.setLayout(1);
        serieDiv29.setRestDuration(120);
        serieDivisionRepository.save(serieDiv29);

        SerieDivision serieDiv30 = new SerieDivision();
        serieDiv30.setNbRep(15);
        serieDiv30.setWeight(60.0);
        serieDiv30.setLayout(2);
        serieDiv30.setRestDuration(120);
        serieDivisionRepository.save(serieDiv30);

        SerieDivision serieDiv31 = new SerieDivision();
        serieDiv31.setNbRep(15);
        serieDiv31.setWeight(60.0);
        serieDiv31.setLayout(3);
        serieDiv31.setRestDuration(120);
        serieDivisionRepository.save(serieDiv31);

        SerieDivision serieDiv32 = new SerieDivision();
        serieDiv32.setNbRep(15);
        serieDiv32.setWeight(60.0);
        serieDiv32.setLayout(4);
        serieDiv32.setRestDuration(120);
        serieDivisionRepository.save(serieDiv32);

        // Training methods
        TrainingMethod trainingMethod3x3 = new TrainingMethod();
        trainingMethod3x3.setName("3x3");
        trainingMethod3x3.setRepMax(3);
        trainingMethod3x3.setRepMin(3);
        trainingMethod3x3.setWeightMax(90.0);
        trainingMethod3x3.setWeightMin(90.0);
        trainingMethod3x3.setSerieDivisions(new ArrayList<>(Arrays.asList(serieDiv1, serieDiv2, serieDiv3)));
        trainingMethodRepository.save(trainingMethod3x3);

        TrainingMethod trainingMethod4x4 = new TrainingMethod();
        trainingMethod4x4.setName("4x4");
        trainingMethod4x4.setRepMax(4);
        trainingMethod4x4.setRepMin(4);
        trainingMethod4x4.setWeightMax(87.0);
        trainingMethod4x4.setWeightMin(87.0);
        trainingMethod4x4.setSerieDivisions(new ArrayList<>(Arrays.asList(serieDiv4, serieDiv5, serieDiv6, serieDiv7)));
        trainingMethodRepository.save(trainingMethod4x4);

        TrainingMethod trainingMethod5x5 = new TrainingMethod();
        trainingMethod5x5.setName("5x5");
        trainingMethod5x5.setRepMax(5);
        trainingMethod5x5.setRepMin(5);
        trainingMethod5x5.setWeightMax(85.0);
        trainingMethod5x5.setWeightMin(85.0);
        trainingMethod5x5.setSerieDivisions(new ArrayList<>(Arrays.asList(serieDiv8, serieDiv9, serieDiv10, serieDiv11, serieDiv12)));
        trainingMethodRepository.save(trainingMethod5x5);

        TrainingMethod trainingMethodBulgare4 = new TrainingMethod();
        trainingMethodBulgare4.setName("Bulgare 4");
        trainingMethodBulgare4.setRepMax(10);
        trainingMethodBulgare4.setRepMin(6);
        trainingMethodBulgare4.setWeightMax(50.0);
        trainingMethodBulgare4.setWeightMin(85.0);
        trainingMethodBulgare4.setSerieDivisions(new ArrayList<>(Arrays.asList(serieDiv13, serieDiv14, serieDiv15, serieDiv16, serieDiv17, serieDiv18,serieDiv19, serieDiv20)));
        trainingMethodRepository.save(trainingMethodBulgare4);

        TrainingMethod trainingMethodChargeConstante = new TrainingMethod();
        trainingMethodChargeConstante.setName("Charge constante");
        trainingMethodChargeConstante.setRepMax(10);
        trainingMethodChargeConstante.setRepMin(10);
        trainingMethodChargeConstante.setWeightMax(75.0);
        trainingMethodChargeConstante.setWeightMin(75.0);
        trainingMethodChargeConstante.setSerieDivisions(new ArrayList<>(Arrays.asList(serieDiv21, serieDiv22, serieDiv23, serieDiv24)));
        trainingMethodRepository.save(trainingMethodChargeConstante);

        TrainingMethod trainingMethod4x15 = new TrainingMethod();
        trainingMethod4x15.setName("4x15");
        trainingMethod4x15.setRepMax(15);
        trainingMethod4x15.setRepMin(15);
        trainingMethod4x15.setWeightMax(60.0);
        trainingMethod4x15.setWeightMin(60.0);
        trainingMethod4x15.setSerieDivisions(new ArrayList<>(Arrays.asList(serieDiv25, serieDiv26, serieDiv27, serieDiv28)));
        trainingMethodRepository.save(trainingMethod4x15);

        TrainingMethod trainingMethod4x10 = new TrainingMethod();
        trainingMethod4x10.setName("4x10");
        trainingMethod4x10.setRepMax(10);
        trainingMethod4x10.setRepMin(10);
        trainingMethod4x10.setWeightMax(70.0);
        trainingMethod4x10.setWeightMin(70.0);
        trainingMethod4x10.setSerieDivisions(new ArrayList<>(Arrays.asList(serieDiv29, serieDiv30, serieDiv31, serieDiv32)));
        trainingMethodRepository.save(trainingMethod4x10);

        // Training type
        TrainingType full = new TrainingType();
        full.setName("full");
        full.setDescription("no description");
        trainingTypeRepository.save(full);

        // Exercises
        Exercise devCouchBarre = new Exercise();
        devCouchBarre.setName("développer couché barre");
        devCouchBarre.setDescription("");
        devCouchBarre.setEquipments(new ArrayList<>(Arrays.asList(poidslibres)));
        devCouchBarre.setMorphologies(new ArrayList<>(Arrays.asList(breviligne)));
        devCouchBarre.setBiomecanicFunctions(new ArrayList<>(Arrays.asList(brasAdduction, coudeExtension)));
        exerciseRepository.save(devCouchBarre);

        Exercise devCouchHaltere = new Exercise();
        devCouchHaltere.setName("développer couché haltères");
        devCouchHaltere.setDescription("");
        devCouchHaltere.setEquipments(new ArrayList<>(Arrays.asList(poidslibres)));
        devCouchHaltere.setMorphologies(new ArrayList<>(Arrays.asList(longiligne)));
        devCouchHaltere.setBiomecanicFunctions(new ArrayList<>(Arrays.asList(brasAdduction, coudeExtension)));
        exerciseRepository.save(devCouchHaltere);

        Exercise pompes = new Exercise();
        pompes.setName("pompes");
        pompes.setDescription("");
        pompes.setEquipments(new ArrayList<>());
        pompes.setMorphologies(new ArrayList<>(Arrays.asList(breviligne, longiligne)));
        pompes.setBiomecanicFunctions(new ArrayList<>(Arrays.asList(brasAdduction, coudeExtension)));
        exerciseRepository.save(pompes);

        Exercise souleveTerreTradi = new Exercise();
        souleveTerreTradi.setName("souleveTerreTradi");
        souleveTerreTradi.setDescription("");
        souleveTerreTradi.setEquipments(new ArrayList<>(Arrays.asList(poidslibres)));
        souleveTerreTradi.setMorphologies(new ArrayList<>(Arrays.asList(breviligne)));
        souleveTerreTradi.setBiomecanicFunctions(new ArrayList<>(Arrays.asList(hanchesExtension)));
        exerciseRepository.save(souleveTerreTradi);

        Exercise souleveTerreSumo = new Exercise();
        souleveTerreSumo.setName("souleveTerreSumo");
        souleveTerreSumo.setDescription("");
        souleveTerreSumo.setEquipments(new ArrayList<>(Arrays.asList(poidslibres)));
        souleveTerreSumo.setMorphologies(new ArrayList<>(Arrays.asList(longiligne)));
        souleveTerreSumo.setBiomecanicFunctions(new ArrayList<>(Arrays.asList(hanchesExtension)));
        exerciseRepository.save(souleveTerreSumo);

        Exercise squatTradi = new Exercise();
        squatTradi.setName("squatTradi");
        squatTradi.setDescription("");
        squatTradi.setEquipments(new ArrayList<>(Arrays.asList(poidslibres)));
        squatTradi.setMorphologies(new ArrayList<>(Arrays.asList(breviligne, longiligne)));
        squatTradi.setBiomecanicFunctions(new ArrayList<>(Arrays.asList(hanchesExtension, hanchesFlexion, jambeFlexion, jambeExtension, jambeAdduction, jambeAbduction)));
        exerciseRepository.save(squatTradi);

        Exercise frontSquat = new Exercise();
        frontSquat.setName("frontSquat");
        frontSquat.setDescription("");
        frontSquat.setEquipments(new ArrayList<>(Arrays.asList(halteres)));
        frontSquat.setMorphologies(new ArrayList<>(Arrays.asList(breviligne)));
        frontSquat.setBiomecanicFunctions(new ArrayList<>(Arrays.asList(chevilleExtensionFlexion, hanchesExtension, hanchesFlexion, brasAntepulsion, jambeFlexion, jambeExtension, jambeAdduction, jambeAdduction)));
        exerciseRepository.save(frontSquat);

        Exercise squatSumo = new Exercise();
        squatSumo.setName("squatSumo");
        squatSumo.setDescription("");
        squatSumo.setEquipments(new ArrayList<>(Arrays.asList(poidslibres)));
        squatSumo.setMorphologies(new ArrayList<>(Arrays.asList(longiligne)));
        squatSumo.setBiomecanicFunctions(new ArrayList<>(Arrays.asList(hanchesExtension, hanchesFlexion, jambeFlexion, jambeExtension, jambeAdduction, jambeAbduction)));
        exerciseRepository.save(squatSumo);

        Exercise tractionPronation = new Exercise();
        tractionPronation.setName("tractionPronation");
        tractionPronation.setDescription("");
        tractionPronation.setEquipments(new ArrayList<>(Arrays.asList(machines)));
        tractionPronation.setMorphologies(new ArrayList<>(Arrays.asList(breviligne)));
        tractionPronation.setBiomecanicFunctions(new ArrayList<>(Arrays.asList(brasAbduction, coudeFlexion)));
        exerciseRepository.save(tractionPronation);

        Exercise tractionSupination = new Exercise();
        tractionSupination.setName("tractionSupination");
        tractionSupination.setDescription("");
        tractionSupination.setEquipments(new ArrayList<>(Arrays.asList(machines)));
        tractionSupination.setMorphologies(new ArrayList<>(Arrays.asList(breviligne)));
        tractionSupination.setBiomecanicFunctions(new ArrayList<>(Arrays.asList(brasAbduction, coudeFlexion)));
        exerciseRepository.save(tractionSupination);

        Exercise tirageVerticalPronation = new Exercise();
        tirageVerticalPronation.setName("tirageVerticalPronation");
        tirageVerticalPronation.setDescription("");
        tirageVerticalPronation.setEquipments(new ArrayList<>(Arrays.asList(machines)));
        tirageVerticalPronation.setMorphologies(new ArrayList<>(Arrays.asList(longiligne, breviligne)));
        tirageVerticalPronation.setBiomecanicFunctions(new ArrayList<>(Arrays.asList(brasAbduction, coudeFlexion)));
        exerciseRepository.save(tirageVerticalPronation);

        Exercise tirageVerticalSupination = new Exercise();
        tirageVerticalSupination.setName("tirageVerticalSupination");
        tirageVerticalSupination.setDescription("");
        tirageVerticalSupination.setEquipments(new ArrayList<>(Arrays.asList(machines)));
        tirageVerticalSupination.setMorphologies(new ArrayList<>(Arrays.asList(breviligne, longiligne)));
        tirageVerticalSupination.setBiomecanicFunctions(new ArrayList<>(Arrays.asList(brasAbduction, coudeFlexion)));
        exerciseRepository.save(tirageVerticalSupination);

        Exercise tractionElastiqueSupination = new Exercise();
        tractionElastiqueSupination.setName("tractionElastiqueSupination");
        tractionElastiqueSupination.setDescription("");
        tractionElastiqueSupination.setEquipments(new ArrayList<>(Arrays.asList(machines, elastiques)));
        tractionElastiqueSupination.setMorphologies(new ArrayList<>(Arrays.asList(breviligne, longiligne)));
        tractionElastiqueSupination.setBiomecanicFunctions(new ArrayList<>(Arrays.asList(brasAbduction, coudeFlexion)));
        exerciseRepository.save(tractionElastiqueSupination);

        Exercise tractionElastiquePronation = new Exercise();
        tractionElastiquePronation.setName("tractionElastiquePronation");
        tractionElastiquePronation.setDescription("");
        tractionElastiquePronation.setEquipments(new ArrayList<>(Arrays.asList(machines, elastiques)));
        tractionElastiquePronation.setMorphologies(new ArrayList<>(Arrays.asList(breviligne, longiligne)));
        tractionElastiquePronation.setBiomecanicFunctions(new ArrayList<>(Arrays.asList(brasAbduction, coudeFlexion)));
        exerciseRepository.save(tractionElastiquePronation);

        Exercise rowingBarrePronation = new Exercise();
        rowingBarrePronation.setName("rowingBarrePronation");
        rowingBarrePronation.setDescription("");
        rowingBarrePronation.setEquipments(new ArrayList<>(Arrays.asList(poidslibres)));
        rowingBarrePronation.setMorphologies(new ArrayList<>(Arrays.asList(breviligne)));
        rowingBarrePronation.setBiomecanicFunctions(new ArrayList<>(Arrays.asList(brasAbduction, coudeFlexion)));
        exerciseRepository.save(rowingBarrePronation);


        Exercise rowingBarreSupination = new Exercise();
        rowingBarreSupination.setName("rowingBarreSupination");
        rowingBarreSupination.setDescription("");
        rowingBarreSupination.setEquipments(new ArrayList<>(Arrays.asList(poidslibres)));
        rowingBarreSupination.setMorphologies(new ArrayList<>(Arrays.asList(breviligne)));
        rowingBarreSupination.setBiomecanicFunctions(new ArrayList<>(Arrays.asList(brasAbduction, coudeFlexion)));
        exerciseRepository.save(rowingBarreSupination);

        Exercise rowingMachinePronation = new Exercise();
        rowingMachinePronation.setName("rowingMachinePronation");
        rowingMachinePronation.setDescription("");
        rowingMachinePronation.setEquipments(new ArrayList<>(Arrays.asList(machines)));
        rowingMachinePronation.setMorphologies(new ArrayList<>(Arrays.asList(longiligne, breviligne)));
        rowingMachinePronation.setBiomecanicFunctions(new ArrayList<>(Arrays.asList(brasAbduction, coudeFlexion)));
        exerciseRepository.save(rowingMachinePronation);

        Exercise rowingMachineSupination = new Exercise();
        rowingMachineSupination.setName("rowingMachineSupination");
        rowingMachineSupination.setDescription("");
        rowingMachineSupination.setEquipments(new ArrayList<>(Arrays.asList(machines)));
        rowingMachineSupination.setMorphologies(new ArrayList<>(Arrays.asList(breviligne, longiligne)));
        rowingMachineSupination.setBiomecanicFunctions(new ArrayList<>(Arrays.asList(brasAbduction, coudeFlexion)));
        exerciseRepository.save(rowingMachineSupination);

        Exercise rowingMachineNeutre = new Exercise();
        rowingMachineNeutre.setName("rowingMachineNeutre");
        rowingMachineNeutre.setDescription("");
        rowingMachineNeutre.setEquipments(new ArrayList<>(Arrays.asList(machines)));
        rowingMachineNeutre.setMorphologies(new ArrayList<>(Arrays.asList(breviligne ,longiligne)));
        rowingMachineNeutre.setBiomecanicFunctions(new ArrayList<>(Arrays.asList(brasAbduction, coudeFlexion)));
        exerciseRepository.save(rowingMachineNeutre);

        Exercise abdoLeveJambes = new Exercise();
        abdoLeveJambes.setName("abdoLeveJambes");
        abdoLeveJambes.setDescription("");
        abdoLeveJambes.setEquipments(new ArrayList<>());
        abdoLeveJambes.setMorphologies(new ArrayList<>(Arrays.asList(breviligne, longiligne)));
        abdoLeveJambes.setBiomecanicFunctions(new ArrayList<>(Arrays.asList(hanchesFlexion)));
        exerciseRepository.save(abdoLeveJambes);

        Exercise abdoCrunch = new Exercise();
        abdoCrunch.setName("abdoCrunch");
        abdoCrunch.setDescription("");
        abdoCrunch.setEquipments(new ArrayList<>());
        abdoCrunch.setMorphologies(new ArrayList<>(Arrays.asList(breviligne, longiligne)));
        abdoCrunch.setBiomecanicFunctions(new ArrayList<>(Arrays.asList(hanchesFlexion)));
        exerciseRepository.save(abdoCrunch);

        Exercise larsenPressBarre = new Exercise();
        larsenPressBarre.setName("larsenPressBarre");
        larsenPressBarre.setDescription("");
        larsenPressBarre.setEquipments(new ArrayList<>(Arrays.asList(poidslibres)));
        larsenPressBarre.setMorphologies(new ArrayList<>(Arrays.asList(breviligne)));
        larsenPressBarre.setBiomecanicFunctions(new ArrayList<>(Arrays.asList(brasAdduction, coudeExtension)));
        exerciseRepository.save(larsenPressBarre);

        Exercise larsenPressHatlere = new Exercise();
        larsenPressHatlere.setName("larsenPressHatlere");
        larsenPressHatlere.setDescription("");
        larsenPressHatlere.setEquipments(new ArrayList<>(Arrays.asList(poidslibres)));
        larsenPressHatlere.setMorphologies(new ArrayList<>(Arrays.asList(longiligne)));
        larsenPressHatlere.setBiomecanicFunctions(new ArrayList<>(Arrays.asList(brasAdduction, coudeExtension)));
        exerciseRepository.save(larsenPressHatlere);

        Exercise benchPoseBarre = new Exercise();
        benchPoseBarre.setName("benchPoseBarre");
        benchPoseBarre.setDescription("");
        benchPoseBarre.setEquipments(new ArrayList<>(Arrays.asList(poidslibres)));
        benchPoseBarre.setMorphologies(new ArrayList<>(Arrays.asList(breviligne)));
        benchPoseBarre.setBiomecanicFunctions(new ArrayList<>(Arrays.asList(brasAdduction, coudeExtension)));
        exerciseRepository.save(benchPoseBarre);

        Exercise benchPoseHaltere = new Exercise();
        benchPoseHaltere.setName("benchPoseHaltere");
        benchPoseHaltere.setDescription("");
        benchPoseHaltere.setEquipments(new ArrayList<>(Arrays.asList(poidslibres)));
        benchPoseHaltere.setMorphologies(new ArrayList<>(Arrays.asList(longiligne)));
        benchPoseHaltere.setBiomecanicFunctions(new ArrayList<>(Arrays.asList(brasAdduction, coudeExtension)));
        exerciseRepository.save(benchPoseHaltere);



        //Exercises types
        ExerciseType polyStandart = new ExerciseType();
        polyStandart.setName("polyStandart");
        polyStandart.setDescription("");
        polyStandart.setExercises(new ArrayList<>(Arrays.asList(devCouchBarre, devCouchHaltere, pompes, souleveTerreTradi, souleveTerreTradi, squatTradi, frontSquat, squatSumo, tractionPronation,tractionSupination, tirageVerticalPronation, tirageVerticalSupination, tractionElastiqueSupination, tractionElastiquePronation, rowingBarrePronation, rowingBarreSupination, rowingMachinePronation, rowingMachineSupination, rowingMachineNeutre)));
        exerciseTypeRepository.save(polyStandart);

        ExerciseType monoStandart = new ExerciseType();
        monoStandart.setName("monoStandart");
        monoStandart.setDescription("");
        monoStandart.setExercises(new ArrayList<>(Arrays.asList(abdoLeveJambes, abdoCrunch)));
        exerciseTypeRepository.save(monoStandart);

        ExerciseType polyForce = new ExerciseType();
        polyForce.setName("polyForce");
        polyForce.setDescription("");
        polyForce.setExercises(new ArrayList<>(Arrays.asList(devCouchBarre, devCouchHaltere, souleveTerreTradi,souleveTerreSumo, squatTradi, squatSumo)));
        exerciseTypeRepository.save(polyForce);

        ExerciseType polyTechnique = new ExerciseType();
        polyTechnique.setName("polyTechnique");
        polyTechnique.setDescription("");
        polyTechnique.setExercises(new ArrayList<>(Arrays.asList(frontSquat, larsenPressBarre, larsenPressHatlere, benchPoseBarre, benchPoseHaltere)));
        exerciseTypeRepository.save(polyTechnique);

        ExerciseType pdcMachineLibreCircuit1 = new ExerciseType();
        pdcMachineLibreCircuit1.setName("pdcMachineLibreCircuit1");
        pdcMachineLibreCircuit1.setDescription("");
        pdcMachineLibreCircuit1.setExercises(new ArrayList<>(Arrays.asList(tractionPronation, tractionSupination, tirageVerticalPronation, tirageVerticalSupination ,tractionElastiqueSupination, tractionElastiqueSupination, rowingMachinePronation, rowingMachineSupination, rowingMachineNeutre, abdoLeveJambes, abdoCrunch)));
        exerciseTypeRepository.save(pdcMachineLibreCircuit1);

        ExerciseType complexeCircuit2 = new ExerciseType();
        complexeCircuit2.setName("complexeCircuit2");
        complexeCircuit2.setDescription("");
        complexeCircuit2.setExercises(new ArrayList<>(Arrays.asList(devCouchHaltere, devCouchHaltere, pompes, souleveTerreTradi, souleveTerreSumo, squatTradi, squatSumo, tractionPronation, tractionSupination, tractionElastiqueSupination, tractionElastiquePronation, rowingBarrePronation, rowingBarreSupination)));
        exerciseTypeRepository.save(complexeCircuit2);

        //Trainings
        Training full2A2hforce = new Training();
        full2A2hforce.setName("full2A2hforce");
        full2A2hforce.setDescription("");
        full2A2hforce.setTrainingType(full);
        full2A2hforce.setLayout(1);
        full2A2hforce.setDuration(120);
        trainingRepository.save(full2A2hforce);

        Training full2B2hforce = new Training();
        full2B2hforce.setName("full2B2hforce");
        full2B2hforce.setDescription("");
        full2B2hforce.setTrainingType(full);
        full2B2hforce.setLayout(2);
        full2B2hforce.setDuration(120);
        trainingRepository.save(full2B2hforce);

        Training full2A1h30force = new Training();
        full2A1h30force.setName("full2A1h30force");
        full2A1h30force.setDescription("");
        full2A1h30force.setTrainingType(full);
        full2A1h30force.setLayout(1);
        full2A1h30force.setDuration(90);
        trainingRepository.save(full2A1h30force);

        Training full2B1h30force = new Training();
        full2B1h30force.setName("full2B1h30force");
        full2B1h30force.setDescription("");
        full2B1h30force.setTrainingType(full);
        full2B1h30force.setLayout(2);
        full2B1h30force.setDuration(90);
        trainingRepository.save(full2B1h30force);


        //Training components

        TrainingComponent trainingComponent1 = new TrainingComponent();
        trainingComponent1.setTrainingComponentId(new TrainingComponentId(full2A2hforce, trainingMethod4x15, polyStandart, un, 3));
        trainingComponent1.setIsSuperSet(false);
        trainingComponentRepository.save(trainingComponent1);

        TrainingComponent trainingComponent2 = new TrainingComponent();
        trainingComponent2.setTrainingComponentId(new TrainingComponentId(full2A2hforce, trainingMethod4x15, polyStandart, deux, 4));
        trainingComponent2.setIsSuperSet(false);
        trainingComponentRepository.save(trainingComponent2);

        TrainingComponent trainingComponent3 = new TrainingComponent();
        trainingComponent3.setTrainingComponentId(new TrainingComponentId(full2A2hforce, trainingMethod4x15, monoStandart, trois, 5));
        trainingComponent3.setIsSuperSet(false);
        trainingComponentRepository.save(trainingComponent3);

        TrainingComponent trainingComponent4 = new TrainingComponent();
        trainingComponent4.setTrainingComponentId(new TrainingComponentId(full2A2hforce, trainingMethod5x5, polyForce, quatre, 1));
        trainingComponent4.setIsSuperSet(false);
        trainingComponentRepository.save(trainingComponent4);

        TrainingComponent trainingComponent5 = new TrainingComponent();
        trainingComponent5.setTrainingComponentId(new TrainingComponentId(full2A2hforce, trainingMethod5x5, polyTechnique, cinq, 2));
        trainingComponent5.setIsSuperSet(false);
        trainingComponentRepository.save(trainingComponent5);

        TrainingComponent trainingComponent6 = new TrainingComponent();
        trainingComponent6.setTrainingComponentId(new TrainingComponentId(full2B2hforce, trainingMethod4x15, polyStandart, six, 4));
        trainingComponent6.setIsSuperSet(false);
        trainingComponentRepository.save(trainingComponent6);

        TrainingComponent trainingComponent7 = new TrainingComponent();
        trainingComponent7.setTrainingComponentId(new TrainingComponentId(full2B2hforce, trainingMethod3x3, polyForce, quatre, 1));
        trainingComponent7.setIsSuperSet(false);
        trainingComponentRepository.save(trainingComponent7);

        TrainingComponent trainingComponent8 = new TrainingComponent();
        trainingComponent8.setTrainingComponentId(new TrainingComponentId(full2B2hforce, trainingMethod3x3, polyForce, cinq, 2));
        trainingComponent8.setIsSuperSet(false);
        trainingComponentRepository.save(trainingComponent8);

        TrainingComponent trainingComponent9 = new TrainingComponent();
        trainingComponent9.setTrainingComponentId(new TrainingComponentId(full2B2hforce, trainingMethod3x3, polyForce, neuf, 3));
        trainingComponent9.setIsSuperSet(false);
        trainingComponentRepository.save(trainingComponent9);

        TrainingComponent trainingComponent10 = new TrainingComponent();
        trainingComponent10.setTrainingComponentId(new TrainingComponentId(full2A1h30force, trainingMethod4x15, polyStandart, six, 3));
        trainingComponent10.setIsSuperSet(false);
        trainingComponentRepository.save(trainingComponent10);

        TrainingComponent trainingComponent11 = new TrainingComponent();
        trainingComponent11.setTrainingComponentId(new TrainingComponentId(full2A1h30force, trainingMethod4x15, monoStandart, onze, 4));
        trainingComponent11.setIsSuperSet(false);
        trainingComponentRepository.save(trainingComponent11);

        TrainingComponent trainingComponent12 = new TrainingComponent();
        trainingComponent12.setTrainingComponentId(new TrainingComponentId(full2A1h30force, trainingMethod5x5, polyForce, quatre, 1));
        trainingComponent12.setIsSuperSet(false);
        trainingComponentRepository.save(trainingComponent12);

        TrainingComponent trainingComponent13 = new TrainingComponent();
        trainingComponent13.setTrainingComponentId(new TrainingComponentId(full2A1h30force, trainingMethod4x10, polyTechnique, cinq, 2));
        trainingComponent13.setIsSuperSet(false);
        trainingComponentRepository.save(trainingComponent13);

        TrainingComponent trainingComponent14 = new TrainingComponent();
        trainingComponent14.setTrainingComponentId(new TrainingComponentId(full2B1h30force, trainingMethod4x15, polyStandart, six, 3));
        trainingComponent14.setIsSuperSet(false);
        trainingComponentRepository.save(trainingComponent14);

        TrainingComponent trainingComponent15 = new TrainingComponent();
        trainingComponent15.setTrainingComponentId(new TrainingComponentId(full2B1h30force, trainingMethod3x3, polyForce, neuf, 1));
        trainingComponent15.setIsSuperSet(false);
        trainingComponentRepository.save(trainingComponent15);

        TrainingComponent trainingComponent16 = new TrainingComponent();
        trainingComponent16.setTrainingComponentId(new TrainingComponentId(full2B1h30force, trainingMethod3x3, polyForce, cinq, 2));
        trainingComponent16.setIsSuperSet(false);
        trainingComponentRepository.save(trainingComponent16);

        // Programs
        Program program2_2h = new Program();
        program2_2h.setName("2-2h");
        program2_2h.setGoal(force);
        program2_2h.setAvailabilities(new ArrayList<>(Arrays.asList(un120m, deux120m)));
        program2_2h.setTrainings(new ArrayList<>(Arrays.asList(full2A2hforce,full2B2hforce)));
        programRepository.save(program2_2h);

        Program program2_1h30 = new Program();
        program2_1h30.setName("2-1h30");
        program2_1h30.setGoal(force);
        program2_1h30.setAvailabilities(new ArrayList<>(Arrays.asList(un90m, deux90m)));
        program2_1h30.setTrainings(new ArrayList<>(Arrays.asList(full2A1h30force,full2B1h30force)));
        programRepository.save(program2_1h30);


        User userTest = new User();
        userTest.setUsername("vincent");
        userTest.setEmail("vincent@vincent.com");
        userTest.setIsAdmin(true);
        userTest.setAvailabilities(new ArrayList<>(Arrays.asList(un90m, deux120m)));
        userTest.setEquipments(new ArrayList<>(Arrays.asList(halteres, poidslibres, elastiques, machines, parcdestreetworkout)));
        userTest.setGoal(force);
        userTest.setInjuries(new ArrayList<>());
        userTest.setMorphology(longiligne);
        userRepository.save(userTest);
    }
}
