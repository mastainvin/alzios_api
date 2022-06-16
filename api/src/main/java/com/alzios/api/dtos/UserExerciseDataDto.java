package com.alzios.api.dtos;

public class UserExerciseDataDto {
    private Integer nbDone;
    private Double mark;
    private Double weight;

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
}
