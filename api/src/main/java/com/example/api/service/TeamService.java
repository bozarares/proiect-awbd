package com.example.api.service;

import com.example.api.entity.Team;
import com.example.api.entity.TeamMember;
import com.example.api.entity.User;
import com.example.api.repository.TeamMemberRepository;
import com.example.api.repository.TeamRepository;
import com.example.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    public List<Team> getAllTeams() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);

        List<TeamMember> teamMembers = teamMemberRepository.findByUser(user);
        return teamMembers.stream()
                .map(TeamMember::getTeam)
                .collect(Collectors.toList());
    }

    public Optional<Team> getTeamById(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);

        Optional<Team> team = teamRepository.findById(id);
        if (team.isPresent() && teamMemberRepository.findByTeamAndUser(team.get(), user).isPresent()) {
            return team;
        } else {
            return Optional.empty();
        }
    }

    public Team createTeam(Team team) {
        // Obține utilizatorul autentificat
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = userRepository.findByUsername(username);

        // Setează utilizatorul curent ca proprietar al echipei
        team.setUser(currentUser);

        // Salvează echipa
        Team savedTeam = teamRepository.save(team);

        // Creează un membru de echipă pentru utilizatorul curent
        TeamMember teamMember = new TeamMember();
        teamMember.setTeam(savedTeam);
        teamMember.setUser(currentUser);

        // Adaugă membrul de echipă în lista de membri ai echipei
        List<TeamMember> teamMembers = new ArrayList<>();
        teamMembers.add(teamMember);
        savedTeam.setTeamMembers(teamMembers);

        // Salvează membrul de echipă
        teamMemberRepository.save(teamMember);

        return savedTeam;
    }

    public Team updateTeam(Long id, Team teamDetails) {
        Team team = teamRepository.findById(id).orElse(null);
        if (team != null) {
            team.setName(teamDetails.getName());
            team.setDescription(teamDetails.getDescription());
            return teamRepository.save(team);
        }
        return null;
    }

    public void deleteTeam(Long id) {
        teamRepository.deleteById(id);
    }
}
