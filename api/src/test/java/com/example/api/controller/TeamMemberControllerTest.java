package com.example.api.controller;

import com.example.api.entity.TeamMember;
import com.example.api.service.TeamMemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TeamMemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeamMemberService teamMemberService;

    private TeamMember teamMember;

    @BeforeEach
    public void setUp() {
        // Inițializăm obiectul teamMember pentru a fi folosit în teste
        teamMember = new TeamMember();
        teamMember.setId(1L);
        // Inițializăm alte câmpuri necesare pentru teamMember dacă este cazul
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void testCreateTeamMember() throws Exception {
        // Setăm comportamentul mock pentru metoda createTeamMember din
        // teamMemberService
        when(teamMemberService.createTeamMember(any(TeamMember.class))).thenReturn(teamMember);

        // Executăm cererea POST pentru a crea un nou membru al echipei
        mockMvc.perform(post("/api/teamMembers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"team\": {\"id\": 1}, \"user\": {\"id\": 1}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void testAddTeamMemberByEmail() throws Exception {
        // Setăm comportamentul mock pentru metoda addTeamMemberByEmail din
        // teamMemberService
        when(teamMemberService.addTeamMemberByEmail(eq(1L), eq("user@example.com"))).thenReturn(teamMember);

        // Executăm cererea POST pentru a adăuga un membru al echipei prin email
        mockMvc.perform(post("/api/teamMembers/addByEmail")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"teamId\": 1, \"email\": \"user@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void testDeleteTeamMember() throws Exception {
        // Executăm cererea DELETE pentru a șterge un membru al echipei
        mockMvc.perform(delete("/api/teamMembers/1"))
                .andExpect(status().isOk());

        // Verificăm că metoda deleteTeamMember din teamMemberService a fost apelată o
        // dată
        Mockito.verify(teamMemberService, Mockito.times(1)).deleteTeamMember(1L);
    }
}
