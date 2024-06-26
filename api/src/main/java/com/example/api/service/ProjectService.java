package com.example.api.service;

import com.example.api.entity.Project;
import com.example.api.entity.TeamMember;
import com.example.api.entity.User;
import com.example.api.repository.ProjectRepository;
import com.example.api.repository.TeamMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    private UserService userService;

    // Obține toate proiectele asociate utilizatorului autentificat
    public List<Project> getAllProjects() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userService.findByUsername(currentUsername);

        List<TeamMember> teamMembers = teamMemberRepository.findByUser(currentUser);
        List<Project> projects = teamMembers.stream()
                .map(tm -> tm.getTeam().getProjects())
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList());
        return projects;
    }

    // Obține un proiect după ID, doar dacă utilizatorul este membru al echipei
    public Optional<Project> getProjectById(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userService.findByUsername(currentUsername);

        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            List<TeamMember> teamMembers = teamMemberRepository.findByUser(currentUser);
            boolean isMember = teamMembers.stream()
                    .anyMatch(tm -> tm.getTeam().getProjects().contains(project.get()));
            if (isMember) {
                return project;
            } else {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    // Creează un nou proiect
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    // Actualizează un proiect existent
    public Project updateProject(Long id, Project projectDetails) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            project.setName(projectDetails.getName());
            project.setDescription(projectDetails.getDescription());
            project.setStartDate(projectDetails.getStartDate());
            project.setEndDate(projectDetails.getEndDate());
            return projectRepository.save(project);
        }
        return null;
    }

    // Șterge un proiect după ID
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}
