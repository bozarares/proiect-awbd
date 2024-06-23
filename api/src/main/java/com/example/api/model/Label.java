package com.example.api.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String color;

    @ManyToMany(mappedBy = "labels")
    private List<Task> tasks;

    // Getters and setters
}
