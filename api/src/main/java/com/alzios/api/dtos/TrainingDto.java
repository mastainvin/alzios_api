package com.alzios.api.dtos;

import java.util.ArrayList;
import java.util.List;

public class TrainingDto implements Comparable<TrainingDto>{
    private Long id;
    private List<TrainingComponentDto> trainingComponents = new ArrayList<>();
    private Integer layout;
    private Integer duration;

    public List<TrainingComponentDto> getTrainingComponents() {
        return trainingComponents;
    }

    public void setTrainingComponents(List<TrainingComponentDto> trainingComponents) {
        this.trainingComponents = trainingComponents;
    }

    public Integer getLayout() {
        return layout;
    }

    public void setLayout(Integer layout) {
        this.layout = layout;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int compareTo(TrainingDto o) {
        return this.getLayout().compareTo(o.getLayout());
    }
}
