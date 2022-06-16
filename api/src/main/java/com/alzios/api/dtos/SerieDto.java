package com.alzios.api.dtos;

public class SerieDto {
    private Integer expectedRep;
    private Double expectedWeight;

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
}
