package com.alzios.api.domain;

import com.alzios.api.domain.embeddedIds.TrainingComponentId;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Objects;

/**
 * Represents a component in the training.
 */
@Table(name = "training_component")
@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "trainingComponentId")

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
    private Integer nbExerciseInComponent = 1;

    @Column(name = "in_training")
    private Boolean inTraining = true;

    public Boolean getInTraining() {
        return inTraining;
    }

    public void setInTraining(Boolean inTraining) {
        this.inTraining = inTraining;
    }


    public Integer getNbExerciseInComponent() {
        return nbExerciseInComponent;
    }

    public void setNbExerciseInComponent(Integer nbExerciseInComponent) {
        this.nbExerciseInComponent = nbExerciseInComponent;
    }

    public Boolean getIsSuperSet() {
        return isSuperSet;
    }

    public void setIsSuperSet(Boolean isSuperSet) {
        this.isSuperSet = isSuperSet;
    }

    @Override
    public String toString() {
        return "TrainingComponent{" +
                "trainingComponentId=" + trainingComponentId +
                ", isSuperSet=" + isSuperSet +
                ", nbExerciseIncomponent=" + nbExerciseInComponent +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainingComponent that = (TrainingComponent) o;
        return Objects.equals(trainingComponentId, that.trainingComponentId) && Objects.equals(isSuperSet, that.isSuperSet) && Objects.equals(nbExerciseInComponent, that.nbExerciseInComponent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainingComponentId, isSuperSet, nbExerciseInComponent);
    }
}