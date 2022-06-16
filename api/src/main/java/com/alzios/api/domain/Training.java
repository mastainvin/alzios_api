package com.alzios.api.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "training_type_id")
    private TrainingType trainingType;

    @OrderBy("trainingComponentId.layout  ASC")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "training_id")
    private List<TrainingComponent> trainingComponents = new ArrayList<>();

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
}