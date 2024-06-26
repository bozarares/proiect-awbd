package com.example.api.controller;

import com.example.api.entity.Team;
import com.example.api.service.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeamService teamService;

    private Team team;
    private Team updatedTeam;

    @BeforeEach
    public void setUp() {
        // Setăm datele pentru echipa de test
        team = new Team();
        team.setId(1L);
        team.setName("Test Team");
        team.setDescription("Test Description");

        // Setăm datele pentru echipa actualizată
        updatedTeam = new Team();
        updatedTeam.setId(1L);
        updatedTeam.setName("Updated Team");
        updatedTeam.setDescription("Updated Description");
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void testGetAllTeams() throws Exception {
        // Executăm cererea GET pentru a obține toate echipele
        mockMvc.perform(get("/api/teams"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void testGetTeamById() throws Exception {
        // Setăm comportamentul mock pentru serviciul TeamService
        when(teamService.getTeamById(1L)).thenReturn(Optional.of(team));

        // Executăm cererea GET pentru a obține echipa cu id-ul 1
        mockMvc.perform(get("/api/teams/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Team"));
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void testCreateTeam() throws Exception {
        // Setăm comportamentul mock pentru serviciul TeamService
        when(teamService.createTeam(any(Team.class))).thenReturn(team);

        // Executăm cererea POST pentru a crea o nouă echipă
        mockMvc.perform(post("/api/teams")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Test Team\", \"description\": \"Test Description\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Team"))
                .andExpect(jsonPath("$.description").value("Test Description"));
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void testUpdateTeam() throws Exception {
        // Setăm comportamentul mock pentru serviciul TeamService
        when(teamService.updateTeam(eq(1L), any(Team.class))).thenReturn(updatedTeam);

        // Executăm cererea PUT pentru a actualiza echipa cu id-ul 1
        mockMvc.perform(put("/api/teams/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Updated Team\", \"description\": \"Updated Description\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Team"))
                .andExpect(jsonPath("$.description").value("Updated Description"));
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void testDeleteTeam() throws Exception {
        // Executăm cererea DELETE pentru a șterge echipa cu id-ul 1
        mockMvc.perform(delete("/api/teams/1"))
                .andExpect(status().isOk());
    }
}
