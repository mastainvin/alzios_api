package com.alzios.api.dtos;


import java.sql.Date;

public class SerieDto {
    private Long id;
    private Integer expectedRep;
    private Double expectedWeight;
    private Integer layout;
    private Integer restDuration;
    private Integer repetitions;
    private Double weight;
    private Double rpe;
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public Double getRpe() {
        return rpe;
    }

    public void setRpe(Double rpe) {
        this.rpe = rpe;
    }

    public Integer getLayout() {
        return layout;
    }

    public void setLayout(Integer layout) {
        this.layout = layout;
    }

    public Integer getRestDuration() {
        return restDuration;
    }

    public void setRestDuration(Integer restDuration) {
        this.restDuration = restDuration;
    }

    public Integer getExpectedRep() {
        return expectedRep;
    }

    public void setExpectedRep(Integer expectedRep) {
        this.expectedRep = expectedRep;
    }

    public Double getExpectedWeight() {
        return expectedWeight;
    }

    public void setExpectedWeight(Double expectedWeight) {
        this.expectedWeight = expectedWeight;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
