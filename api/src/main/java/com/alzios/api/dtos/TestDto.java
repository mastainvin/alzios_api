package com.alzios.api.dtos;

import com.alzios.api.domain.Availability;
import com.alzios.api.domain.Equipment;
import com.alzios.api.domain.Goal;
import com.alzios.api.domain.Morphology;

import java.util.List;

public class TestDto {
    private Goal goal;
    private Morphology morphology;
    private List<Availability> availabilities;
    private List<Equipment> equipments;

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public List<Availability> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<Availability> availabilities) {
        this.availabilities = availabilities;
    }

    public List<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }

    public Morphology getMorphology() {
        return morphology;
    }

    public void setMorphology(Morphology morphology) {
        this.morphology = morphology;
    }
}
