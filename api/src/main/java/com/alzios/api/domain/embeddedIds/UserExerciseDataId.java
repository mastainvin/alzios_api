package com.alzios.api.domain.embeddedIds;

import com.alzios.api.domain.Exercise;
import com.alzios.api.domain.User;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserExerciseDataId implements Serializable {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public UserExerciseDataId(Exercise exercise, User user) {
        this.exercise = exercise;
        this.user = user;
    }

    public UserExerciseDataId() {

    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserExerciseDataId that = (UserExerciseDataId) o;
        return Objects.equals(exercise, that.exercise) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exercise, user);
    }
}