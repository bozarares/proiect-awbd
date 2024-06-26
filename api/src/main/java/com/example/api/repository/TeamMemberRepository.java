package com.example.api.repository;

import com.example.api.entity.Team;
import com.example.api.entity.TeamMember;
import com.example.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {

    // Metodă pentru a găsi membrii echipei după utilizator
    List<TeamMember> findByUser(User user);

    // Metodă pentru a găsi un membru al echipei după echipă și utilizator
    Optional<TeamMember> findByTeamAndUser(Team team, User user);
}
