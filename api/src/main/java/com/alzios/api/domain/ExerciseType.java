package com.alzios.api.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "exercise_type")
@Entity
public class ExerciseType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = 1000)
    private String description;

    @OrderBy("name ASC")
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "exercise_type_exercises",
            joinColumns = @JoinColumn(name = "exercise_type_id"),
            inverseJoinColumns = @JoinColumn(name = "exercises_id"))

    private List<Exercise> exercises = new ArrayList<>();

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}