package com.alzios.api.dtos;

import java.util.ArrayList;
import java.util.List;

public class TrainingDto implements Comparable<TrainingDto>{
    private Long id;
    private List<TrainingComponentDto> trainingComponents = new ArrayList<>();
    private Integer layout;
    private Integer duration;
    private String name;
    private String description;
    private  Integer intensity;
    private Boolean isDone;

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public  Integer getIntensity() {
        return intensity;
    }

    public void setIntensity(Integer intensity) {
        this.intensity = intensity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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
