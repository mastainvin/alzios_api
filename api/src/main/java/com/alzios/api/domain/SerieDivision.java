package com.alzios.api.domain;

import javax.persistence.*;

@Table(name = "serie_division")
@Entity
public class SerieDivision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nb_rep", nullable = false)
    private Integer nbRep;

    @Column(name = "weight", nullable = false)
    private Double weight;

    @Column(name = "layout", nullable = false)
    private Integer layout;

    @Column(name = "rest_duration", nullable = false)
    private Integer restDuration;

    public Integer getRestDuration() {
        return restDuration;
    }

    public void setRestDuration(Integer restDuration) {
        this.restDuration = restDuration;
    }

    public Integer getLayout() {
        return layout;
    }

    public void setLayout(Integer layout) {
        this.layout = layout;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getNbRep() {
        return nbRep;
    }

    public void setNbRep(Integer nbRep) {
        this.nbRep = nbRep;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}