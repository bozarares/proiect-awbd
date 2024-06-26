package com.example.api.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class TeamMember {

    // ID-ul unic al membrului echipei
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Echipa la care aparține membrul (relație ManyToOne)
    @ManyToOne
    @JoinColumn(name = "team_id")
    @JsonBackReference
    private Team team;

    // Utilizatorul asociat cu membrul echipei (relație ManyToOne)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Getteri și setteri

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
