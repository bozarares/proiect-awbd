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

    public List<TeamMember> getAllTeamMembers() {
        return teamMemberRepository.findAll();
    }

    public TeamMember getTeamMemberById(Long id) {
        return teamMemberRepository.findById(id).orElse(null);
    }

    public TeamMember createTeamMember(TeamMember teamMember) {
        return teamMemberRepository.save(teamMember);
    }

    public TeamMember updateTeamMember(Long id, TeamMember teamMemberDetails) {
        TeamMember teamMember = teamMemberRepository.findById(id).orElse(null);
        if (teamMember != null) {
            teamMember.setTeam(teamMemberDetails.getTeam());
            teamMember.setUser(teamMemberDetails.getUser());
            return teamMemberRepository.save(teamMember);
        }
        return null;
    }

    public void deleteTeamMember(Long id) {
        teamMemberRepository.deleteById(id);
    }

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
