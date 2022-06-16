package com.alzios.api.logic;

import java.util.ArrayList;
import java.util.List;

public class ProgramLogic {
    List<TrainingLogic> trainingLogicList = new ArrayList<>();

    public List<TrainingLogic> getTrainingLogicList() {
        return trainingLogicList;
    }

    public void setTrainingLogicList(List<TrainingLogic> trainingLogicList) {
        this.trainingLogicList = trainingLogicList;
    }
}
