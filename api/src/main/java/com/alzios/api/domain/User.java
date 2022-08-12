package com.alzios.api.domain;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Table(name = "user")
@Entity
public class User {
    @Id
    @Column(name = "id", nullable = false, length = 50)
    private String id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "is_admin", nullable = false)
    private Boolean isAdmin = false;

    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "level")
    private Long level;

    @Column(name = "experience_point")
    private Long experiencePoint;

    @Column(name = "experience_to_reach")
    private Long experienceToReach;

    @Column(name = "equipments_choosen")
    private Boolean equipmentsChoosen = false;

    @Nullable
    @OrderBy("layout ASC")
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "user_availabilities",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "availability_id", referencedColumnName = "id"))
    private List<Availability> availabilities = new ArrayList<>();

    @Nullable
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "goal_id")
    private Goal goal;

    @Nullable
    @OrderBy("name ASC")
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_body_limbs",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "body_limbs_id"))
    private List<BodyLimb> injuries = new ArrayList<>();

    @OrderBy("name ASC")
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_equipments",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "equipments_id"))
    private List<Equipment> equipments = new ArrayList<>();

    @Nullable
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "morphology_id")
    private Morphology morphology;

    public Boolean getEquipmentsChoosen() {
        return equipmentsChoosen;
    }

    public void setEquipmentsChoosen(Boolean equipmentsChoosen) {
        this.equipmentsChoosen = equipmentsChoosen;
    }

    public Long getExperienceToReach() {
        return experienceToReach;
    }

    public void setExperienceToReach(Long experienceToReach) {
        this.experienceToReach = experienceToReach;
    }

    public Long getExperiencePoint() {
        return experiencePoint;
    }

    public void setExperiencePoint(Long experiencePoint) {
        this.experiencePoint = experiencePoint;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Morphology getMorphology() {
        return morphology;
    }

    public void setMorphology(Morphology morphology) {
        this.morphology = morphology;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public List<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }

    public List<BodyLimb> getInjuries() {
        return injuries;
    }

    public void setInjuries(List<BodyLimb> injuries) {
        this.injuries = injuries;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void updateLevel() {
        if(experiencePoint >= experienceToReach){
            level++;
            experiencePoint = experiencePoint - experienceToReach;
            this.updateExperienceToReach();
        }
    }

    private void updateExperienceToReach() {
        this.experienceToReach = 530*Math.round(Math.log(this.level+1));
    }
}