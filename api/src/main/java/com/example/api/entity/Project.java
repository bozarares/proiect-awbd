package com.example.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Project {

    // ID-ul unic al proiectului
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Numele proiectului
    @NotBlank(message = "Name is required")
    private String name;

    // Descrierea proiectului
    @NotBlank(message = "Description is required")
    private String description;

    // Data de început a proiectului
    @NotNull(message = "Start Date is required")
    private LocalDate startDate;

    // Data de sfârșit a proiectului
    @NotNull(message = "End Date is required")
    private LocalDate endDate;

    // Echipa asociată proiectului (relație ManyToOne)
    @ManyToOne
    @JoinColumn(name = "team_id")
    @JsonIgnoreProperties({ "projects", "name", "description", "user", "teamMembers" })
    private Team team;

    // Task-urile asociate proiectului (relație OneToMany)
    @JsonIgnoreProperties({ "comments", "labels" })
    @OneToMany(mappedBy = "project")
    private List<Task> tasks;

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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
