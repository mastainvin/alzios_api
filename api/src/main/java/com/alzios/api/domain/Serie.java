package com.alzios.api.domain;

import javax.persistence.*;
import java.sql.Date;

@Table(name = "serie")
@Entity
public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JoinColumns({
            @JoinColumn(name = "TRAINING_COMPONENT_LAYOUT", referencedColumnName = "LAYOUT"),
            @JoinColumn(name = "TRAINING_COMPONENT_TRAINING_ID", referencedColumnName = "TRAINING_ID"),
            @JoinColumn(name = "TRAINING_COMPONENT_TRAINING_METHOD_ID", referencedColumnName = "TRAINING_METHOD_ID"),
            @JoinColumn(name = "TRAINING_COMPONENT_EXERCISE_TYPE_ID", referencedColumnName = "EXERCISE_TYPE_ID"),
            @JoinColumn(name = "TRAINING_COMPONENT_BIOMECANIC_FUNCTION_LIST_ID", referencedColumnName = "BIOMECANIC_FUNCTION_LIST_ID")
    })
    @ManyToOne(cascade = CascadeType.ALL)
    private TrainingComponent trainingComponent;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "date")
    private Date date;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "repetitions")
    private Integer repetitions;

    @Column(name = "rpe")
    private Integer rpe;

    @Column(name = "expected_weight")
    private Double expectedWeight;

    @Column(name = "expected_repetitions")
    private Integer expectedRepetitions;

    @Column(name = "layout")
    private Integer layout;

    @Column(name = "in_actual_week")
    private Boolean inActualWeek;

    @Column(name = "rest_duration")
    private Integer restDuration;

    public Integer getRestDuration() {
        return restDuration;
    }

    public void setRestDuration(Integer restDuration) {
        this.restDuration = restDuration;
    }

    public Boolean getInActualWeek() {
        return inActualWeek;
    }

    public void setInActualWeek(Boolean inActualWeek) {
        this.inActualWeek = inActualWeek;
    }

    public Integer getLayout() {
        return layout;
    }

    public void setLayout(Integer layout) {
        this.layout = layout;
    }

    public Integer getExpectedRepetitions() {
        return expectedRepetitions;
    }

    public void setExpectedRepetitions(Integer expectedRepetitions) {
        this.expectedRepetitions = expectedRepetitions;
    }

    public Double getExpectedWeight() {
        return expectedWeight;
    }

    public void setExpectedWeight(Double expectedWeight) {
        this.expectedWeight = expectedWeight;
    }

    public Integer getRpe() {
        return rpe;
    }

    public void setRpe(Integer rpe) {
        this.rpe = rpe;
    }

    public Integer getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(Integer repetitions) {
        this.repetitions = repetitions;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public TrainingComponent getTrainingComponent() {
        return trainingComponent;
    }

    public void setTrainingComponent(TrainingComponent trainingComponent) {
        this.trainingComponent = trainingComponent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}