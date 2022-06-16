package com.alzios.api.dtos;

import java.util.ArrayList;
import java.util.List;

public class ProgramDto {
    private List<TrainingDto> trainings = new ArrayList<>();

    public List<TrainingDto> getTrainings() {
        return trainings;
    }

    public void setTrainings(List<TrainingDto> trainings) {
        this.trainings = trainings;
    }
}
