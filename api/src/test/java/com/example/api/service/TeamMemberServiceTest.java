package com.example.api.service;

import com.example.api.entity.Team;
import com.example.api.entity.TeamMember;
import com.example.api.entity.User;
import com.example.api.repository.TeamMemberRepository;
import com.example.api.repository.TeamRepository;
import com.example.api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TeamMemberServiceTest {

    @Mock
    private TeamMemberRepository teamMemberRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamMemberService teamMemberService;

    private TeamMember teamMember;
    private Team team;
    private User user;

    @BeforeEach
    public void setUp() {
        // Inițializăm entitatea TeamMember
        teamMember = new TeamMember();
        teamMember.setId(1L);

        // Inițializăm entitatea Team
        team = new Team();
        team.setId(1L);

        // Inițializăm entitatea User
        user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");

        // Setăm relațiile între entități
        teamMember.setTeam(team);
        teamMember.setUser(user);
    }

    @Test
    public void testCreateTeamMember() {
        // Mock pentru salvarea unui membru al echipei
        when(teamMemberRepository.save(any(TeamMember.class))).thenReturn(teamMember);

        // Apelăm metoda pentru crearea unui membru al echipei
        TeamMember createdTeamMember = teamMemberService.createTeamMember(teamMember);

        // Verificăm dacă membrul echipei creat nu este nul și are ID-ul așteptat
        assertNotNull(createdTeamMember);
        assertEquals(teamMember.getId(), createdTeamMember.getId());
        verify(teamMemberRepository).save(any(TeamMember.class));
    }

    @Test
    public void testUpdateTeamMember() {
        // Mock pentru găsirea și salvarea unui membru al echipei
        when(teamMemberRepository.findById(1L)).thenReturn(Optional.of(teamMember));
        when(teamMemberRepository.save(any(TeamMember.class))).thenReturn(teamMember);

        // Inițializăm detaliile actualizate ale membrului echipei
        TeamMember teamMemberDetails = new TeamMember();
        teamMemberDetails.setTeam(new Team());
        teamMemberDetails.setUser(new User());

        // Apelăm metoda pentru actualizarea unui membru al echipei
        TeamMember updatedTeamMember = teamMemberService.updateTeamMember(1L, teamMemberDetails);

        // Verificăm dacă membrul echipei actualizat nu este nul
        assertNotNull(updatedTeamMember);
        verify(teamMemberRepository).findById(1L);
        verify(teamMemberRepository).save(any(TeamMember.class));
    }

    @Test
    public void testDeleteTeamMember() {
        // Apelăm metoda pentru ștergerea unui membru al echipei
        teamMemberService.deleteTeamMember(1L);

        // Verificăm dacă metoda de ștergere a fost apelată cu ID-ul așteptat
        verify(teamMemberRepository).deleteById(1L);
    }

    @Test
    public void testAddTeamMemberByEmail() {
        // Mock pentru găsirea unui utilizator după email și a unui team după ID
        when(userRepository.findByEmail(eq("user@example.com"))).thenReturn(Optional.of(user));
        when(teamRepository.findById(eq(1L))).thenReturn(Optional.of(team));
        when(teamMemberRepository.save(any(TeamMember.class))).thenReturn(teamMember);

        // Apelăm metoda pentru adăugarea unui membru al echipei prin email
        TeamMember addedTeamMember = teamMemberService.addTeamMemberByEmail(1L, "user@example.com");

        // Verificăm dacă membrul echipei adăugat nu este nul și are ID-ul așteptat
        assertNotNull(addedTeamMember);
        assertEquals(teamMember.getId(), addedTeamMember.getId());
        verify(userRepository).findByEmail(eq("user@example.com"));
        verify(teamRepository).findById(eq(1L));
        verify(teamMemberRepository).save(any(TeamMember.class));
    }
}
