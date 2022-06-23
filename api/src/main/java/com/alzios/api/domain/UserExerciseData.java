package com.alzios.api.domain;

import com.alzios.api.domain.embeddedIds.UserExerciseDataId;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Table(name = "user_exercise_data")
@Entity
public class UserExerciseData {
    @EmbeddedId
    private UserExerciseDataId userExerciseDataId;

    @Column(name = "weight", nullable = false)
    private Double weight = 0.0;

    @Column(name = "mark", nullable = false)
    private Double mark = 5.0;

    @Column(name = "desired_number_in_training")
    private Integer desiredNumberInTraining = 1;

    @Column(name = "nb_done")
    private Integer nbDone = 0;

    public Integer getDesiredNumberInTraining() {
        return desiredNumberInTraining;
    }

    public void setDesiredNumberInTraining(Integer desiredNumberInTraining) {
        this.desiredNumberInTraining = desiredNumberInTraining;
    }

    public Integer getNbDone() {
        return nbDone;
    }

    public void setNbDone(Integer nbDone) {
        this.nbDone = nbDone;
    }

    public Double getMark() {
        return mark;
    }

    public void setMark(Double mark) {
        this.mark = mark;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public UserExerciseDataId getUserExerciseDataId() {
        return userExerciseDataId;
    }

    public void setUserExerciseDataId(UserExerciseDataId userExerciseDataId) {
        this.userExerciseDataId = userExerciseDataId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserExerciseData that = (UserExerciseData) o;
        return Objects.equals(userExerciseDataId, that.userExerciseDataId) && Objects.equals(weight, that.weight) && Objects.equals(mark, that.mark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userExerciseDataId, weight, mark);
    }
}