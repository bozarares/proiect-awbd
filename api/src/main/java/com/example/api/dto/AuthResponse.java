package com.example.api.dto;

import com.example.api.entity.User;

/**
 * DTO pentru răspunsul de autentificare.
 */
public class AuthResponse {
    private String token;
    private User user;

    /**
     * Constructorul cu parametri.
     *
     * @param token Token-ul JWT.
     * @param user  Obiectul utilizatorului.
     */
    public AuthResponse(String token, User user) {
        this.token = token;
        this.user = user;
    }

    // Getters și setters

    /**
     * Returnează token-ul JWT.
     *
     * @return Token-ul JWT.
     */
    public String getToken() {
        return token;
    }

    /**
     * Setează token-ul JWT.
     *
     * @param token Token-ul JWT.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Returnează utilizatorul.
     *
     * @return Utilizatorul.
     */
    public User getUser() {
        return user;
    }

    /**
     * Setează utilizatorul.
     *
     * @param user Utilizatorul.
     */
    public void setUser(User user) {
        this.user = user;
    }
}
