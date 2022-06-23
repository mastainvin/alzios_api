package com.alzios.api.domain;

import com.alzios.api.domain.embeddedIds.TrainingComponentId;

import javax.persistence.*;

/**
 * Represents a component in the training.
 */
@Table(name = "training_component")
@Entity
public class TrainingComponent {
    @EmbeddedId
    private TrainingComponentId trainingComponentId;

    public TrainingComponentId getTrainingComponentId() {
        return this.trainingComponentId;
    }

    public void setTrainingComponentId(TrainingComponentId trainingComponentId) {
        this.trainingComponentId = trainingComponentId;
    }

    @Column(name = "is_super_set", nullable = false)
    private Boolean isSuperSet = false;

    @Column(name = "nb_exercise_incomponent")
    private Integer nbExerciseIncomponent = 1;

    public Integer getNbExerciseIncomponent() {
        return nbExerciseIncomponent;
    }

    public void setNbExerciseIncomponent(Integer nbExerciseIncomponent) {
        this.nbExerciseIncomponent = nbExerciseIncomponent;
    }

    public Boolean getIsSuperSet() {
        return isSuperSet;
    }

    public void setIsSuperSet(Boolean isSuperSet) {
        this.isSuperSet = isSuperSet;
    }
}