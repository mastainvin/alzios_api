package com.alzios.api.dtos;

import com.alzios.api.domain.Exercise;
import com.alzios.api.domain.TrainingMethod;

import java.util.List;

public class TrainingComponentDto implements Comparable<TrainingComponentDto>{
    private List<ExerciseDto> exercises;
    private ExerciseDto exerciseChosen;
    private Integer layout;
    private TrainingMethod trainingMethod;
    private UserExerciseDataDto data;
    private List<SerieDto> series;
    private Boolean isSuperSet;

    public Boolean getSuperSet() {
        return isSuperSet;
    }

    public void setSuperSet(Boolean superSet) {
        isSuperSet = superSet;
    }

    public List<SerieDto> getSeries() {
        return series;
    }

    public void setSeries(List<SerieDto> series) {
        this.series = series;
    }

    public List<ExerciseDto> getExercises() {
        return exercises;
    }

    public void setExercises(List<ExerciseDto> exercises) {
        this.exercises = exercises;
    }

    public ExerciseDto getExerciseChosen() {
        return exerciseChosen;
    }

    public void setExerciseChosen(ExerciseDto exerciseChosen) {
        this.exerciseChosen = exerciseChosen;
    }

    public Integer getLayout() {
        return layout;
    }

    public void setLayout(Integer layout) {
        this.layout = layout;
    }

    public TrainingMethod getTrainingMethod() {
        return trainingMethod;
    }

    public void setTrainingMethod(TrainingMethod trainingMethod) {
        this.trainingMethod = trainingMethod;
    }

    public UserExerciseDataDto getData() {
        return data;
    }

    public void setData(UserExerciseDataDto data) {
        this.data = data;
    }

    @Override
    public int compareTo(TrainingComponentDto o) {
        return this.getLayout().compareTo(o.getLayout());
    }
}
