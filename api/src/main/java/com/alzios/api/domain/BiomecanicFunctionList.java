package com.alzios.api.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name = "biomecanic_function_list")
@Entity
public class BiomecanicFunctionList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OrderBy("name ASC")
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "biomecanic_function_list_biomecanic_functions",
            joinColumns = @JoinColumn(name = "biomecanic_function_list_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "biomecanic_functions_id", referencedColumnName = "id"))
    private List<BiomecanicFunction> biomecanicFunctions = new ArrayList<>();


    public List<BiomecanicFunction> getBiomecanicFunctions() {
        return biomecanicFunctions;
    }

    public void setBiomecanicFunctions(List<BiomecanicFunction> biomecanicFunctions) {
        this.biomecanicFunctions = biomecanicFunctions;
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
        BiomecanicFunctionList that = (BiomecanicFunctionList) o;
        return Objects.equals(id, that.id) && Objects.equals(biomecanicFunctions, that.biomecanicFunctions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, biomecanicFunctions);
    }
}