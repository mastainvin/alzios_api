package com.alzios.api.domain;

import javax.persistence.*;

@Table(name = "body_limb")
@Entity
public class BodyLimb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "upper")
    private Boolean upper = false;

    @Column(name = "lower")
    private Boolean lower;

    public Boolean getLower() {
        return lower;
    }

    public void setLower(Boolean lower) {
        this.lower = lower;
    }

    public Boolean getUpper() {
        return upper;
    }

    public void setUpper(Boolean upper) {
        this.upper = upper;
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
}