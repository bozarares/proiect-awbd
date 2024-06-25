package com.example.api.controller;

import com.example.api.entity.TeamMember;
import com.example.api.service.TeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teamMembers")
public class TeamMemberController {

    @Autowired
    private TeamMemberService teamMemberService;

    @GetMapping
    public List<TeamMember> getAllTeamMembers() {
        return teamMemberService.getAllTeamMembers();
    }

    @GetMapping("/{id}")
    public TeamMember getTeamMemberById(@PathVariable Long id) {
        return teamMemberService.getTeamMemberById(id);
    }

    @PostMapping
    public TeamMember createTeamMember(@RequestBody TeamMember teamMember) {
        return teamMemberService.createTeamMember(teamMember);
    }

    @PutMapping("/{id}")
    public TeamMember updateTeamMember(@PathVariable Long id, @RequestBody TeamMember teamMemberDetails) {
        return teamMemberService.updateTeamMember(id, teamMemberDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteTeamMember(@PathVariable Long id) {
        teamMemberService.deleteTeamMember(id);
    }

    @PostMapping("/addByEmail")
    public TeamMember addTeamMemberByEmail(@RequestBody AddTeamMemberRequest request) {
        return teamMemberService.addTeamMemberByEmail(request.getTeamId(), request.getEmail());
    }
}

class AddTeamMemberRequest {
    private Long teamId;
    private String email;

    // Getters and setters
    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
