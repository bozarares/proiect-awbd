package com.example.api.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO pentru cererea de autentificare.
 */
public class AuthRequest {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    // Getters și setters

    /**
     * Returnează username-ul.
     *
     * @return Username-ul.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setează username-ul.
     *
     * @param username Username-ul.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returnează parola.
     *
     * @return Parola.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setează parola.
     *
     * @param password Parola.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
