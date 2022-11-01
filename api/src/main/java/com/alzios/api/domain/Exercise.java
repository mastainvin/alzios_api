package com.alzios.api.domain;

import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name = "exercise")
@Entity
public class Exercise extends RepresentationModel<Exercise> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "default_number_in_program")
    private Integer defaultNumberInProgram = 1;

    @Column(name = "picture")
    private String picture;

    @Column(name = "video")
    private String video;

    @OrderBy("name ASC")
    @ManyToMany
    @JoinTable(name = "exercise_biomecanic_functions",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "biomecanic_functions_id"))
    private List<BiomecanicFunction> biomecanicFunctions = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "exercise_morphologies",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "morphologies_id"))
    private List<Morphology> morphologies = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "exercise_equipment_lists")
    private List<EquipmentList> equipmentLists = new ArrayList<>();

    public Integer getDefaultNumberInProgram() {
        return defaultNumberInProgram;
    }

    public void setDefaultNumberInProgram(Integer default_number_in_program) {
        this.defaultNumberInProgram = default_number_in_program;
    }

    public List<EquipmentList> getEquipmentLists() {
        return equipmentLists;
    }

    public void setEquipmentLists(List<EquipmentList> equipmentLists) {
        this.equipmentLists = equipmentLists;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }


    public List<Morphology> getMorphologies() {
        return morphologies;
    }

    public void setMorphologies(List<Morphology> morphologies) {
        this.morphologies = morphologies;
    }

    public List<BiomecanicFunction> getBiomecanicFunctions() {
        return biomecanicFunctions;
    }

    public void setBiomecanicFunctions(List<BiomecanicFunction> biomecanicFunctions) {
        this.biomecanicFunctions = biomecanicFunctions;
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
        if (!super.equals(o)) return false;
        Exercise exercise = (Exercise) o;
        return Objects.equals(id, exercise.id) && Objects.equals(name, exercise.name) && Objects.equals(description, exercise.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, name, description);
    }
}