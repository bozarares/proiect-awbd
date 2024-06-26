package com.example.api.controller;

import com.example.api.entity.Team;
import com.example.api.service.TeamService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    private static final Logger logger = LoggerFactory.getLogger(TeamController.class);

    /**
     * Endpoint pentru a obține toate echipele.
     * 
     * @return Lista de echipe.
     */
    @GetMapping
    public List<Team> getAllTeams() {
        logger.info("Fetching all teams");
        return teamService.getAllTeams();
    }

    /**
     * Endpoint pentru a obține o echipă după ID.
     * 
     * @param id ID-ul echipei.
     * @return Echipa găsită sau un mesaj de eroare dacă utilizatorul nu este membru
     *         al echipei.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getTeamById(@PathVariable Long id) {
        logger.info("Fetching team with ID: {}", id);
        Optional<Team> team = teamService.getTeamById(id);
        if (team.isPresent()) {
            return ResponseEntity.ok(team.get());
        } else {
            logger.warn("User is not a team member for team ID: {}", id);
            return ResponseEntity.status(403).body("{\"error\": \"You are not a team member\"}");
        }
    }

    /**
     * Endpoint pentru a crea o echipă nouă.
     * 
     * @param team Echipa de creat.
     * @return Echipa creată.
     */
    @PostMapping
    public Team createTeam(@Valid @RequestBody Team team) {
        logger.info("Creating new team with name: {}", team.getName());
        return teamService.createTeam(team);
    }

    /**
     * Endpoint pentru a actualiza o echipă existentă.
     * 
     * @param id          ID-ul echipei ce trebuie actualizată.
     * @param teamDetails Detaliile actualizate ale echipei.
     * @return Echipa actualizată.
     */
    @PutMapping("/{id}")
    public Team updateTeam(@PathVariable Long id, @RequestBody @Valid Team teamDetails) {
        logger.info("Updating team with ID: {}", id);
        return teamService.updateTeam(id, teamDetails);
    }

    /**
     * Endpoint pentru a șterge o echipă după ID.
     * 
     * @param id ID-ul echipei ce trebuie ștearsă.
     */
    @DeleteMapping("/{id}")
    public void deleteTeam(@PathVariable Long id) {
        logger.info("Deleting team with ID: {}", id);
        teamService.deleteTeam(id);
    }
}
