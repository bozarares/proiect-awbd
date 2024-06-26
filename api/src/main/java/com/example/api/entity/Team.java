package com.example.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Entity
public class Team {

    // ID-ul unic al echipei
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Numele echipei
    @NotBlank(message = "Name is required")
    private String name;

    // Descrierea echipei
    @NotBlank(message = "Description is required")
    private String description;

    // Utilizatorul care a creat echipa (relație ManyToOne)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Membrii echipei (relație OneToMany)
    @OneToMany(mappedBy = "team")
    private List<TeamMember> teamMembers;

    // Proiectele echipei (relație OneToMany)
    @OneToMany(mappedBy = "team")
    private List<Project> projects;

    // Getteri și setteri

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<TeamMember> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(List<TeamMember> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}
