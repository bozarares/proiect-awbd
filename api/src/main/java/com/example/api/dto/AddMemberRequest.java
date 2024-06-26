package com.example.api.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO (Data Transfer Object) pentru cererea de adăugare a unui membru la o
 * echipă.
 */
public class AddMemberRequest {
    private Long teamId;

    @NotBlank(message = "Email Date is required")
    private String email;

    // Getters și setters

    /**
     * Returnează ID-ul echipei.
     *
     * @return ID-ul echipei.
     */
    public Long getTeamId() {
        return teamId;
    }

    /**
     * Setează ID-ul echipei.
     *
     * @param teamId ID-ul echipei.
     */
    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    /**
     * Returnează email-ul.
     *
     * @return Email-ul.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setează email-ul.
     *
     * @param email Email-ul.
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
