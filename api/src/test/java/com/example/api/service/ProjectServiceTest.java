package com.example.api.service;

import com.example.api.entity.Project;
import com.example.api.entity.Team;
import com.example.api.entity.TeamMember;
import com.example.api.entity.User;
import com.example.api.repository.ProjectRepository;
import com.example.api.repository.TeamMemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TeamMemberRepository teamMemberRepository;

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private ProjectService projectService;

    private Project project;
    private User user;
    private TeamMember teamMember;

    @BeforeEach
    public void setUp() {
        // Inițializăm entitatea Project
        project = new Project();
        project.setId(1L);
        project.setName("Test Project");
        project.setDescription("Test Description");
        project.setStartDate(LocalDate.now());
        project.setEndDate(LocalDate.now().plusDays(10));

        // Inițializăm entitatea Team
        Team team = new Team();
        team.setId(1L);
        team.setProjects(Arrays.asList(project));

        // Inițializăm entitatea User
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        // Inițializăm entitatea TeamMember
        teamMember = new TeamMember();
        teamMember.setId(1L);
        teamMember.setTeam(team);
        teamMember.setUser(user);

        // Setăm contextul de securitate pentru autentificare
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Mock pentru obținerea numelui utilizatorului autenticat și a entităților
        // corespunzătoare
        when(authentication.getName()).thenReturn("testuser");
        when(userService.findByUsername("testuser")).thenReturn(user);
        when(teamMemberRepository.findByUser(user)).thenReturn(Arrays.asList(teamMember));
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    public void testGetAllProjects() {
        // Mock pentru returnarea unei liste de proiecte
        when(projectRepository.findAll()).thenReturn(Arrays.asList(project));

        // Apelăm metoda pentru obținerea tuturor proiectelor
        List<Project> projects = projectService.getAllProjects();

        // Verificăm dacă lista de proiecte nu este nulă, nu este goală și conține
        // proiectul așteptat
        assertNotNull(projects);
        assertFalse(projects.isEmpty());
        assertEquals(1, projects.size());
        assertEquals(project.getId(), projects.get(0).getId());
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    public void testGetProjectById() {
        // Mock pentru returnarea unui proiect după ID
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        // Apelăm metoda pentru obținerea proiectului după ID
        Optional<Project> foundProject = projectService.getProjectById(1L);

        // Verificăm dacă proiectul a fost găsit și are ID-ul așteptat
        assertTrue(foundProject.isPresent());
        assertEquals(project.getId(), foundProject.get().getId());
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    public void testCreateProject() {
        // Mock pentru salvarea unui proiect
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        // Apelăm metoda pentru crearea unui proiect
        Project createdProject = projectService.createProject(project);

        // Verificăm dacă proiectul creat nu este nul și are ID-ul așteptat
        assertNotNull(createdProject);
        assertEquals(project.getId(), createdProject.getId());
        verify(projectRepository).save(any(Project.class));
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    public void testUpdateProject() {
        // Mock pentru găsirea și salvarea unui proiect
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        // Inițializăm detaliile actualizate ale proiectului
        Project projectDetails = new Project();
        projectDetails.setName("Updated Project");
        projectDetails.setDescription("Updated Description");
        projectDetails.setStartDate(LocalDate.now().plusDays(1));
        projectDetails.setEndDate(LocalDate.now().plusDays(20));

        // Apelăm metoda pentru actualizarea proiectului
        Project updatedProject = projectService.updateProject(1L, projectDetails);

        // Verificăm dacă proiectul actualizat nu este nul și are detaliile așteptate
        assertNotNull(updatedProject);
        assertEquals(project.getId(), updatedProject.getId());
        assertEquals("Updated Project", updatedProject.getName());
        assertEquals("Updated Description", updatedProject.getDescription());
        assertEquals(projectDetails.getStartDate(), updatedProject.getStartDate());
        assertEquals(projectDetails.getEndDate(), updatedProject.getEndDate());
        verify(projectRepository).findById(1L);
        verify(projectRepository).save(any(Project.class));
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    public void testDeleteProject() {
        // Apelăm metoda pentru ștergerea unui proiect
        projectService.deleteProject(1L);

        // Verificăm dacă metoda de ștergere a fost apelată cu ID-ul așteptat
        verify(projectRepository).deleteById(1L);
    }
}
