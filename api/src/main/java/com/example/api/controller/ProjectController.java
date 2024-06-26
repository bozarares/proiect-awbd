package com.example.api.controller;

import com.example.api.entity.Project;
import com.example.api.service.ProjectService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    /**
     * Endpoint pentru a obține toate proiectele.
     * 
     * @return Lista de proiecte.
     */
    @GetMapping
    public List<Project> getAllProjects() {
        logger.info("Fetching all projects");
        return projectService.getAllProjects();
    }

    /**
     * Endpoint pentru a obține un proiect după ID.
     * 
     * @param id ID-ul proiectului.
     * @return Proiectul găsit sau un mesaj de eroare dacă utilizatorul nu este
     *         membru al echipei.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getProjectById(@PathVariable Long id) {
        logger.info("Fetching project with ID: {}", id);
        Optional<Project> project = projectService.getProjectById(id);
        if (project.isPresent()) {
            logger.info("Project found with ID: {}", id);
            return ResponseEntity.ok(project.get());
        } else {
            logger.warn("User is not a team member for project ID: {}", id);
            return ResponseEntity.status(403).body("{\"error\": \"You are not a team member\"}");
        }
    }

    /**
     * Endpoint pentru a crea un nou proiect.
     * 
     * @param project Proiectul de creat.
     * @return Proiectul creat.
     */
    @PostMapping
    public Project createProject(@RequestBody @Valid Project project) {
        logger.info("Creating new project with name: {}", project.getName());
        return projectService.createProject(project);
    }

    /**
     * Endpoint pentru a actualiza un proiect existent.
     * 
     * @param id             ID-ul proiectului ce trebuie actualizat.
     * @param projectDetails Detaliile actualizate ale proiectului.
     * @return Proiectul actualizat.
     */
    @PutMapping("/{id}")
    public Project updateProject(@PathVariable Long id, @RequestBody @Valid Project projectDetails) {
        logger.info("Updating project with ID: {}", id);
        return projectService.updateProject(id, projectDetails);
    }

    /**
     * Endpoint pentru a șterge un proiect după ID.
     * 
     * @param id ID-ul proiectului ce trebuie șters.
     */
    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        logger.info("Deleting project with ID: {}", id);
        projectService.deleteProject(id);
    }
}
