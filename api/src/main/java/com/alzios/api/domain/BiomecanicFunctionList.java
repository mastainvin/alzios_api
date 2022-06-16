package com.alzios.api.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "biomecanic_function_list")
@Entity
public class BiomecanicFunctionList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OrderBy("name ASC")
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "biomecanic_function_list_biomecanic_functions",
            joinColumns = @JoinColumn(name = "biomecanic_function_list_id"),
            inverseJoinColumns = @JoinColumn(name = "biomecanic_functions_id"))
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
}