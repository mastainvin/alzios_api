package com.alzios.api.logic;

import com.alzios.api.domain.*;

import java.util.*;

public class TrainingComponentLogic implements  Comparable<TrainingComponentLogic>{
    Set<UserExerciseData> userExerciseDataSet = new HashSet<>();
    TrainingComponent trainingComponent;
    UserExerciseData chosenExercise;
    List<Serie> series = new ArrayList<>();

    public TrainingComponentLogic(TrainingComponent trainingComponent) {
        this.trainingComponent = trainingComponent;
    }

    public Set<UserExerciseData> getUserExerciseDataSet() {
        return userExerciseDataSet;
    }

    public void setUserExerciseDataSet(Set<UserExerciseData> userExerciseDataSet) {
        this.userExerciseDataSet = userExerciseDataSet;
    }

    public TrainingComponent getTrainingComponent() {
        return trainingComponent;
    }

    public void setTrainingComponent(TrainingComponent trainingComponent) {
        this.trainingComponent = trainingComponent;
    }

    public UserExerciseData getChosenExercise() {
        return chosenExercise;
    }

    public void setChosenExercise(UserExerciseData chosenExercise) {
        this.chosenExercise = chosenExercise;
    }

    public List<Serie> getSeries() {
        return series;
    }

    public void setSeries(List<Serie> series) {
        this.series = series;
    }

    @Override
    public int compareTo(TrainingComponentLogic o) {
        return this.getTrainingComponent().getTrainingComponentId().getLayout().compareTo(o.getTrainingComponent().getTrainingComponentId().getLayout());
    }
}
