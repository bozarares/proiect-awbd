package com.example.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;

@Entity
public class Task {

    // ID-ul unic al task-ului
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Titlul task-ului
    @NotBlank(message = "Title is required")
    private String title;

    // Descrierea task-ului
    @NotBlank(message = "Description is required")
    private String description;

    // Data de creare a task-ului
    @NotNull(message = "Created Date is required")
    private LocalDate createdDate;

    // Data limită a task-ului
    @NotNull(message = "Due Date is required")
    private LocalDate dueDate;

    // Prioritatea task-ului
    @NotBlank(message = "Priority Date is required")
    private String priority;

    // Statusul task-ului
    @NotBlank(message = "Status Date is required")
    private String status;

    // Proiectul asociat task-ului (relație ManyToOne)
    @ManyToOne
    @JsonIncludeProperties("id")
    @JoinColumn(name = "project_id")
    private Project project;

    // Comentariile asociate task-ului (relație OneToMany)
    @JsonBackReference
    @OneToMany(mappedBy = "task")
    private List<Comment> comments;

    // Etichetele asociate task-ului (relație ManyToMany)
    @ManyToMany
    @JoinTable(name = "task_label", joinColumns = @JoinColumn(name = "task_id"), inverseJoinColumns = @JoinColumn(name = "label_id"))
    private List<Label> labels = new ArrayList<>();

    // Getteri și setteri

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }
}
