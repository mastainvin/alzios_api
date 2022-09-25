package com.alzios.api.logic;


import com.alzios.api.domain.Training;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TrainingLogic implements Comparable<TrainingLogic>{
    private List<TrainingComponentLogic> trainingComponentLogicList = new ArrayList<>();
    private Training training;

    public TrainingLogic(Training training) {
        this.training = training;
    }
    public List<TrainingComponentLogic> getTrainingComponentDtoList() {
        return trainingComponentLogicList;
    }

    public void setTrainingComponentDtoList(List<TrainingComponentLogic> trainingComponentLogicList) {
        this.trainingComponentLogicList = trainingComponentLogicList;
    }

    public List<TrainingComponentLogic> getTrainingComponentLogicList() {
        return trainingComponentLogicList;
    }

    public void setTrainingComponentLogicList(List<TrainingComponentLogic> trainingComponentLogicList) {
        this.trainingComponentLogicList = trainingComponentLogicList;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    @Override
    public int compareTo(TrainingLogic o) {
        return training.getLayout().compareTo(o.getTraining().getLayout());
    }

    @Override
    public String toString() {
        return "TrainingLogic{" +
                "trainingComponentLogicList=" + trainingComponentLogicList +
                ", training=" + training +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainingLogic that = (TrainingLogic) o;
        return Objects.equals(training.getId(), that.getTraining().getId());
    }
}
