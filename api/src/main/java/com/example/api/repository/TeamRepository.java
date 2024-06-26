package com.example.api.repository;

import com.example.api.entity.Team;
import com.example.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    // Metodă pentru a găsi echipele după utilizator
    List<Team> findByUser(User user);
}
