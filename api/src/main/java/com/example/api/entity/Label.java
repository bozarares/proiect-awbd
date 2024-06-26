package com.example.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Label {

    // ID-ul unic al etichetei
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Numele etichetei
    @NotBlank(message = "Name Date is required")
    private String name;

    // Culoarea etichetei
    @NotBlank(message = "Color Date is required")
    private String color;

    // Task-urile asociate etichetei (relație ManyToMany)
    @JsonBackReference
    @ManyToMany(mappedBy = "labels")
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
