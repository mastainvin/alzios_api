package com.alzios.api.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name = "biomecanic_function")
@Entity
public class BiomecanicFunction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name = "biomecanic_function_body_limbs",
            joinColumns = @JoinColumn(name = "biomecanic_function_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "body_limbs_id", referencedColumnName = "id"))
    private List<BodyLimb> bodyLimbs = new ArrayList<>();

    public List<BodyLimb> getBodyLimbs() {
        return bodyLimbs;
    }

    public void setBodyLimbs(List<BodyLimb> bodyLimbs) {
        this.bodyLimbs = bodyLimbs;
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
        BiomecanicFunction that = (BiomecanicFunction) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(bodyLimbs, that.bodyLimbs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, bodyLimbs);
    }
}