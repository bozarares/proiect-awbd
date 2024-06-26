package com.example.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.api.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
