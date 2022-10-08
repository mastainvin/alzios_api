package com.alzios.api.domain;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name = "training")
@Entity
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "layout", nullable = false)
    private Integer layout;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @ManyToOne
    @JoinColumn(name = "training_type_id")
    private TrainingType trainingType;

    @Column(name = "intensity", nullable = false)
    private @PositiveOrZero @Max(10) @Min(0) Integer intensity;

    @OrderBy("trainingComponentId.layout  ASC")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "trainingComponentId.training", orphanRemoval = true)
    private List<TrainingComponent> trainingComponents = new ArrayList<>();

    public @PositiveOrZero @Max(10) @Min(0) Integer getIntensity() {
        return intensity;
    }

    public void setIntensity(@PositiveOrZero @Max(10) @Min(0) Integer intensity) {
        this.intensity = intensity;
    }


    public List<TrainingComponent> getTrainingComponents() {
        return trainingComponents;
    }

    public void setTrainingComponents(List<TrainingComponent> trainingComponents) {
        this.trainingComponents = trainingComponents;
    }

    public TrainingType getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(TrainingType trainingType) {
        this.trainingType = trainingType;
    }


    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getLayout() {
        return layout;
    }

    public void setLayout(Integer layout) {
        this.layout = layout;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Training training = (Training) o;
        return Objects.equals(id, training.id) && Objects.equals(name, training.name) && Objects.equals(description, training.description) && Objects.equals(layout, training.layout) && Objects.equals(duration, training.duration) && Objects.equals(trainingType, training.trainingType) && Objects.equals(trainingComponents, training.trainingComponents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, layout, duration, trainingType, trainingComponents);
    }
}