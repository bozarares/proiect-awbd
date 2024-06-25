package com.example.api.dto;

import jakarta.validation.constraints.NotBlank;

public class AddMemberRequest {
    private Long teamId;

    @NotBlank(message = "Email Date is required")
    private String email;

    // Getters and setters
    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
