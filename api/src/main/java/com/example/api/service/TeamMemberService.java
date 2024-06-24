package com.example.api.service;

import com.example.api.entity.TeamMember;
import com.example.api.repository.TeamMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamMemberService {

    @Autowired
    private TeamMemberRepository teamMemberRepository;

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
}
