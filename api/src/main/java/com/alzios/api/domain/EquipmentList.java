package com.alzios.api.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "equipment_list")
public class EquipmentList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToMany
    @JoinTable(name = "equipment_list_equipments",
            joinColumns = @JoinColumn(name = "equipment_list_id"),
            inverseJoinColumns = @JoinColumn(name = "equipments_id"))
    private List<Equipment> equipments = new ArrayList<>();

    public List<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}