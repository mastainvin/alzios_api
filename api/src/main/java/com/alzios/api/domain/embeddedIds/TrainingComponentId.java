package com.alzios.api.domain.embeddedIds;

import com.alzios.api.domain.BiomecanicFunctionList;
import com.alzios.api.domain.ExerciseType;
import com.alzios.api.domain.Training;
import com.alzios.api.domain.TrainingMethod;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TrainingComponentId implements Serializable {

    private Integer layout;

    @ManyToOne
    @JsonBackReference
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "training_id")
    private Training training;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "training_method_id")
    private TrainingMethod trainingMethod;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "exercise_type_id")
    private ExerciseType exerciseType;

    @OneToOne(orphanRemoval = true)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "biomecanic_function_list_id")
    private BiomecanicFunctionList biomecanicFunctionList;

    public BiomecanicFunctionList getBiomecanicFunctionList() {
        return biomecanicFunctionList;
    }

    public void setBiomecanicFunctionList(BiomecanicFunctionList biomecanicFunctionList) {
        this.biomecanicFunctionList = biomecanicFunctionList;
    }

    public TrainingComponentId(Training training, TrainingMethod trainingMethod, ExerciseType exerciseType, BiomecanicFunctionList biomecanicFunctionList, Integer layout) {
        this.training = training;
        this.trainingMethod = trainingMethod;
        this.exerciseType = exerciseType;
        this.biomecanicFunctionList = biomecanicFunctionList;
        this.layout = layout;
    }

    public TrainingComponentId() {
    }
    public ExerciseType getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(ExerciseType exerciseType) {
        this.exerciseType = exerciseType;
    }

    public TrainingMethod getTrainingMethod() {
        return trainingMethod;
    }

    public void setTrainingMethod(TrainingMethod trainingMethod) {
        this.trainingMethod = trainingMethod;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public Integer getLayout() {
        return layout;
    }

    public void setLayout(Integer layout) {
        this.layout = layout;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainingComponentId that = (TrainingComponentId) o;
        return Objects.equals(layout, that.layout) && Objects.equals(training, that.training) && Objects.equals(trainingMethod, that.trainingMethod) && Objects.equals(exerciseType, that.exerciseType) && Objects.equals(biomecanicFunctionList, that.biomecanicFunctionList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(layout, training, trainingMethod, exerciseType, biomecanicFunctionList);
    }

    @Override
    public String toString() {
        return "TrainingComponentId{" +
                "layout=" + layout +
                ", training=" + training +
                ", trainingMethod=" + trainingMethod +
                ", exerciseType=" + exerciseType +
                ", biomecanicFunctionList=" + biomecanicFunctionList +
                '}';
    }
}
