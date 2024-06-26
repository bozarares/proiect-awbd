package com.example.api.service;

import com.example.api.entity.Team;
import com.example.api.entity.TeamMember;
import com.example.api.entity.User;
import com.example.api.repository.TeamMemberRepository;
import com.example.api.repository.TeamRepository;
import com.example.api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TeamMemberRepository teamMemberRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    private TeamService teamService;

    @BeforeEach
    public void setUp() {
        // Inițializăm mock-urile și instanța TeamService
        MockitoAnnotations.openMocks(this);
        teamService = new TeamService();
        teamService.setTeamRepository(teamRepository);
        teamService.setUserRepository(userRepository);
        teamService.setTeamMemberRepository(teamMemberRepository);

        // Setăm contextul de securitate
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    @Test
    public void testGetAllTeams() {
        // Creăm un utilizator mock
        User user = new User();
        user.setUsername("user");

        // Creăm un team mock
        Team team = new Team();
        team.setName("Test Team");

        // Creăm un membru al echipei mock
        TeamMember teamMember = new TeamMember();
        teamMember.setTeam(team);
        teamMember.setUser(user);

        // Definim comportamentul mock-urilor
        when(authentication.getName()).thenReturn("user");
        when(userRepository.findByUsername("user")).thenReturn(user);
        when(teamMemberRepository.findByUser(user)).thenReturn(Arrays.asList(teamMember));

        // Apelăm metoda getAllTeams din TeamService
        List<Team> teams = teamService.getAllTeams();

        // Verificăm rezultatele
        assertNotNull(teams);
        assertEquals(1, teams.size());
        assertEquals("Test Team", teams.get(0).getName());
    }

    @Test
    public void testGetTeamById() {
        // Creăm un utilizator mock
        User user = new User();
        user.setUsername("user");

        // Creăm un team mock
        Team team = new Team();
        team.setId(1L);
        team.setName("Test Team");

        // Creăm un membru al echipei mock
        TeamMember teamMember = new TeamMember();
        teamMember.setTeam(team);
        teamMember.setUser(user);

        // Definim comportamentul mock-urilor
        when(authentication.getName()).thenReturn("user");
        when(userRepository.findByUsername("user")).thenReturn(user);
        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));
        when(teamMemberRepository.findByTeamAndUser(team, user)).thenReturn(Optional.of(teamMember));

        // Apelăm metoda getTeamById din TeamService
        Optional<Team> foundTeam = teamService.getTeamById(1L);

        // Verificăm rezultatele
        assertTrue(foundTeam.isPresent());
        assertEquals("Test Team", foundTeam.get().getName());
    }

    @Test
    public void testCreateTeam() {
        // Creăm un utilizator mock
        User user = new User();
        user.setUsername("user");

        // Creăm un team mock
        Team team = new Team();
        team.setName("New Team");

        // Definim comportamentul mock-urilor
        when(authentication.getName()).thenReturn("user");
        when(userRepository.findByUsername("user")).thenReturn(user);
        when(teamRepository.save(any(Team.class))).thenAnswer(i -> i.getArguments()[0]);
        when(teamMemberRepository.save(any(TeamMember.class))).thenAnswer(i -> i.getArguments()[0]);

        // Apelăm metoda createTeam din TeamService
        Team createdTeam = teamService.createTeam(team);

        // Verificăm rezultatele
        assertNotNull(createdTeam);
        assertEquals("New Team", createdTeam.getName());
        assertEquals(user, createdTeam.getUser());
        assertEquals(1, createdTeam.getTeamMembers().size());
        assertEquals(user, createdTeam.getTeamMembers().get(0).getUser());
    }
}
