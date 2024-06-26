package com.example.api.controller;

import com.example.api.entity.TeamMember;
import com.example.api.service.TeamMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teamMembers")
public class TeamMemberController {

    @Autowired
    private TeamMemberService teamMemberService;

    private static final Logger logger = LoggerFactory.getLogger(TeamMemberController.class);

    /**
     * Endpoint pentru a obține toți membrii echipei.
     * 
     * @return Lista de membri ai echipei.
     */
    @GetMapping
    public List<TeamMember> getAllTeamMembers() {
        logger.info("Fetching all team members");
        return teamMemberService.getAllTeamMembers();
    }

    /**
     * Endpoint pentru a obține un membru al echipei după ID.
     * 
     * @param id ID-ul membrului echipei.
     * @return Membrul echipei găsit.
     */
    @GetMapping("/{id}")
    public TeamMember getTeamMemberById(@PathVariable Long id) {
        logger.info("Fetching team member with ID: {}", id);
        return teamMemberService.getTeamMemberById(id);
    }

    /**
     * Endpoint pentru a crea un nou membru al echipei.
     * 
     * @param teamMember Membrul echipei de creat.
     * @return Membrul echipei creat.
     */
    @PostMapping
    public TeamMember createTeamMember(@RequestBody TeamMember teamMember) {
        logger.info("Creating new team member for team ID: {}", teamMember.getTeam().getId());
        return teamMemberService.createTeamMember(teamMember);
    }

    /**
     * Endpoint pentru a actualiza un membru al echipei existent.
     * 
     * @param id                ID-ul membrului echipei ce trebuie actualizat.
     * @param teamMemberDetails Detaliile actualizate ale membrului echipei.
     * @return Membrul echipei actualizat.
     */
    @PutMapping("/{id}")
    public TeamMember updateTeamMember(@PathVariable Long id, @RequestBody TeamMember teamMemberDetails) {
        logger.info("Updating team member with ID: {}", id);
        return teamMemberService.updateTeamMember(id, teamMemberDetails);
    }

    /**
     * Endpoint pentru a șterge un membru al echipei după ID.
     * 
     * @param id ID-ul membrului echipei ce trebuie șters.
     */
    @DeleteMapping("/{id}")
    public void deleteTeamMember(@PathVariable Long id) {
        logger.info("Deleting team member with ID: {}", id);
        teamMemberService.deleteTeamMember(id);
    }

    /**
     * Endpoint pentru a adăuga un membru al echipei prin email.
     * 
     * @param request Obiectul de cerere ce conține ID-ul echipei și email-ul.
     * @return Membrul echipei adăugat.
     */
    @PostMapping("/addByEmail")
    public TeamMember addTeamMemberByEmail(@RequestBody AddTeamMemberRequest request) {
        logger.info("Adding team member to team ID: {} with email: {}", request.getTeamId(), request.getEmail());
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
