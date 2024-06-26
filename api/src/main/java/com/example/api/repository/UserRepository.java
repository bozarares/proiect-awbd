package com.example.api.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.api.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Metodă pentru a găsi un utilizator după username
    User findByUsername(String username);

    // Metodă pentru a găsi un utilizator după email
    Optional<User> findByEmail(String email);
}
