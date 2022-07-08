package com.alzios.api.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name = "training_method")
@Entity
public class TrainingMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "rep_max", nullable = false)
    private Integer repMax;

    @Column(name = "rep_min", nullable = false)
    private Integer repMin;

    @Column(name = "weight_max", nullable = false)
    private Double weightMax;

    @Column(name = "weight_min", nullable = false)
    private Double weightMin;

    @OrderBy("layout ASC")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "training_method_id")
    private List<SerieDivision> serieDivisions = new ArrayList<>();

    public List<SerieDivision> getSerieDivisions() {
        return serieDivisions;
    }

    public void setSerieDivisions(List<SerieDivision> serieDivisions) {
        this.serieDivisions = serieDivisions;
    }

    public Double getWeightMin() {
        return weightMin;
    }

    public void setWeightMin(Double weightMin) {
        this.weightMin = weightMin;
    }

    public Double getWeightMax() {
        return weightMax;
    }

    public void setWeightMax(Double weightMax) {
        this.weightMax = weightMax;
    }

    public Integer getRepMin() {
        return repMin;
    }

    public void setRepMin(Integer repMin) {
        this.repMin = repMin;
    }

    public Integer getRepMax() {
        return repMax;
    }

    public void setRepMax(Integer repMax) {
        this.repMax = repMax;
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
        TrainingMethod that = (TrainingMethod) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(repMax, that.repMax) && Objects.equals(repMin, that.repMin) && Objects.equals(weightMax, that.weightMax) && Objects.equals(weightMin, that.weightMin) && Objects.equals(serieDivisions, that.serieDivisions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, repMax, repMin, weightMax, weightMin, serieDivisions);
    }
}