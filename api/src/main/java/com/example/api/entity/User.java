package com.example.api.entity;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "\"user\"", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public class User {

    // ID-ul unic al utilizatorului
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Numele de utilizator
    @Column(nullable = false, unique = true)
    private String username;

    // Parola utilizatorului
    @Column(nullable = false)
    private String password;

    // Email-ul utilizatorului
    @Column(nullable = false, unique = true)
    private String email;

    // Rolul utilizatorului
    private String role;

    // Comentariile scrise de utilizator (relație OneToMany)
    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Comment> comments;

    // Getteri și setteri

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
