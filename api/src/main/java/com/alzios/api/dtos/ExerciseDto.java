package com.alzios.api.dtos;

import com.alzios.api.domain.Equipment;

import java.util.List;

public class ExerciseDto {
    private Long id;
    private String name;
    private String description;
    private String picture;
    private String video;
    private Integer nbDone;
    private Double mark;
    private Double weight;

    private Integer desiredNumberInTraining;
    private List<List<Equipment>> equipmentLists;

    public List<List<Equipment>> getEquipmentLists() {
        return equipmentLists;
    }

    public void setEquipmentLists(List<List<Equipment>> equipmentLists) {
        this.equipmentLists = equipmentLists;
    }

    public String getPicture() {
        return picture;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getDesiredNumberInTraining() {
        return desiredNumberInTraining;
    }

    public void setDesiredNumberInTraining(Integer desiredNumberInTraining) {
        this.desiredNumberInTraining = desiredNumberInTraining;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
