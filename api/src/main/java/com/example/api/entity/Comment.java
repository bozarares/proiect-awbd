package com.example.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Comment {

    // ID-ul unic al comentariului
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Conținutul comentariului
    @NotBlank(message = "Content Date is required")
    private String content;

    // Data la care a fost creat comentariul
    private LocalDate createdDate;

    // Utilizatorul care a scris comentariul (relație ManyToOne)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Task-ul la care este asociat comentariul (relație ManyToOne)
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "task_id")
    private Task task;

    // Getteri și setteri

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
