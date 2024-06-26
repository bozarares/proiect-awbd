package com.example.api.service;

import com.example.api.entity.Team;
import com.example.api.entity.TeamMember;
import com.example.api.entity.User;
import com.example.api.repository.TeamMemberRepository;
import com.example.api.repository.TeamRepository;
import com.example.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamMemberService {

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    // Obține toți membrii echipei
    public List<TeamMember> getAllTeamMembers() {
        return teamMemberRepository.findAll();
    }

    // Obține un membru al echipei după ID
    public TeamMember getTeamMemberById(Long id) {
        return teamMemberRepository.findById(id).orElse(null);
    }

    // Creează un nou membru al echipei
    public TeamMember createTeamMember(TeamMember teamMember) {
        return teamMemberRepository.save(teamMember);
    }

    // Actualizează un membru al echipei existent
    public TeamMember updateTeamMember(Long id, TeamMember teamMemberDetails) {
        TeamMember teamMember = teamMemberRepository.findById(id).orElse(null);
        if (teamMember != null) {
            teamMember.setTeam(teamMemberDetails.getTeam());
            teamMember.setUser(teamMemberDetails.getUser());
            return teamMemberRepository.save(teamMember);
        }
        return null;
    }

    // Șterge un membru al echipei după ID
    public void deleteTeamMember(Long id) {
        teamMemberRepository.deleteById(id);
    }

    // Adaugă un membru al echipei după email
    public TeamMember addTeamMemberByEmail(Long teamId, String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Optional<Team> teamOptional = teamRepository.findById(teamId);
            if (teamOptional.isPresent()) {
                Team team = teamOptional.get();
                TeamMember teamMember = new TeamMember();
                teamMember.setTeam(team);
                teamMember.setUser(user);
                return teamMemberRepository.save(teamMember);
            }
        }
        return null;
    }
}
