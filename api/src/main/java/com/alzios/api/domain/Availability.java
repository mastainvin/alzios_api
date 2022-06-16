package com.alzios.api.domain;

import javax.persistence.*;

@Table(name = "availability")
@Entity
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "layout", nullable = false)
    private Integer layout;

    public Integer getLayout() {
        return layout;
    }

    public void setLayout(Integer layout) {
        this.layout = layout;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}