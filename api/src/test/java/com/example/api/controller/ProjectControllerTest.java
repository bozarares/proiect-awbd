package com.example.api.controller;

import com.example.api.entity.Project;
import com.example.api.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @MockBean
    private SecurityContext securityContext;

    @MockBean
    private Authentication authentication;

    @BeforeEach
    public void setUp() {
        // Setăm contextul de securitate și autentificarea mock
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn("testuser");
    }

    @Test
    @WithMockUser(username = "testuser")
    public void testGetAllProjects() throws Exception {
        // Setăm datele mock pentru un proiect
        Project project = new Project();
        project.setName("Test Project");

        Mockito.when(projectService.getAllProjects()).thenReturn(Arrays.asList(project));

        // Executăm cererea GET
        mockMvc.perform(get("/api/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Project"));
    }

    @Test
    @WithMockUser(username = "testuser")
    public void testGetProjectById() throws Exception {
        // Setăm datele mock pentru un proiect
        Project project = new Project();
        project.setName("Test Project");

        Mockito.when(projectService.getProjectById(eq(1L))).thenReturn(Optional.of(project));

        // Executăm cererea GET
        mockMvc.perform(get("/api/projects/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Project"));
    }

    @Test
    @WithMockUser(username = "testuser")
    public void testCreateProject() throws Exception {
        // Setăm datele mock pentru crearea unui proiect
        Project project = new Project();
        project.setName("New Project");
        project.setDescription("New Description");
        project.setStartDate(LocalDate.now());
        project.setEndDate(LocalDate.now().plusDays(10));

        Mockito.when(projectService.createProject(any(Project.class))).thenReturn(project);

        // Executăm cererea POST
        mockMvc.perform(post("/api/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\"name\": \"New Project\", \"description\": \"New Description\", \"startDate\": \"2024-06-26\", \"endDate\": \"2024-07-06\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Project"))
                .andExpect(jsonPath("$.description").value("New Description"))
                .andExpect(jsonPath("$.startDate").value("2024-06-26"))
                .andExpect(jsonPath("$.endDate").value("2024-07-06"));
    }

    @Test
    @WithMockUser(username = "testuser")
    public void testUpdateProject() throws Exception {
        // Setăm datele mock pentru actualizarea unui proiect
        Project project = new Project();
        project.setName("Updated Project");
        project.setDescription("Updated Description");
        project.setStartDate(LocalDate.now().plusDays(1));
        project.setEndDate(LocalDate.now().plusDays(20));

        Mockito.when(projectService.updateProject(eq(1L), any(Project.class))).thenReturn(project);

        // Executăm cererea PUT
        mockMvc.perform(put("/api/projects/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\"name\": \"Updated Project\", \"description\": \"Updated Description\", \"startDate\": \"2024-06-27\", \"endDate\": \"2024-07-16\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Project"))
                .andExpect(jsonPath("$.description").value("Updated Description"))
                .andExpect(jsonPath("$.startDate").value("2024-06-27"))
                .andExpect(jsonPath("$.endDate").value("2024-07-16"));
    }

    @Test
    @WithMockUser(username = "testuser")
    public void testDeleteProject() throws Exception {
        // Executăm cererea DELETE
        mockMvc.perform(delete("/api/projects/1"))
                .andExpect(status().isOk());

        // Verificăm că metoda serviciului a fost apelată
        Mockito.verify(projectService).deleteProject(1L);
    }
}
