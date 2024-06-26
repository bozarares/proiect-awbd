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

    // Obține toate echipele utilizatorului autentificat
    public List<Team> getAllTeams() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);

        List<TeamMember> teamMembers = teamMemberRepository.findByUser(user);
        return teamMembers.stream()
                .map(TeamMember::getTeam)
                .collect(Collectors.toList());
    }

    // Obține o echipă după ID dacă utilizatorul este membru al echipei
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

    // Creează o nouă echipă și adaugă utilizatorul curent ca membru
    public Team createTeam(Team team) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = userRepository.findByUsername(username);

        team.setUser(currentUser);

        Team savedTeam = teamRepository.save(team);

        TeamMember teamMember = new TeamMember();
        teamMember.setTeam(savedTeam);
        teamMember.setUser(currentUser);

        List<TeamMember> teamMembers = new ArrayList<>();
        teamMembers.add(teamMember);
        savedTeam.setTeamMembers(teamMembers);

        teamMemberRepository.save(teamMember);

        return savedTeam;
    }

    // Actualizează o echipă existentă
    public Team updateTeam(Long id, Team teamDetails) {
        Team team = teamRepository.findById(id).orElse(null);
        if (team != null) {
            team.setName(teamDetails.getName());
            team.setDescription(teamDetails.getDescription());
            return teamRepository.save(team);
        }
        return null;
    }

    // Șterge o echipă după ID
    public void deleteTeam(Long id) {
        teamRepository.deleteById(id);
    }

    // Setteri pentru testare
    public void setTeamRepository(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setTeamMemberRepository(TeamMemberRepository teamMemberRepository) {
        this.teamMemberRepository = teamMemberRepository;
    }
}
