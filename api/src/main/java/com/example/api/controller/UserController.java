package com.example.api.controller;

import com.example.api.entity.User;
import com.example.api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * Endpoint pentru a obține toți utilizatorii.
     * 
     * @return Lista de utilizatori.
     */
    @GetMapping
    public List<User> getAllUsers() {
        logger.info("Fetching all users");
        return userService.getAllUsers();
    }

    /**
     * Endpoint pentru a obține un utilizator după ID.
     * 
     * @param id ID-ul utilizatorului.
     * @return Utilizatorul găsit.
     */
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        logger.info("Fetching user with ID: {}", id);
        return userService.getUserById(id);
    }

    /**
     * Endpoint pentru a crea un nou utilizator.
     * 
     * @param user Utilizatorul de creat.
     * @return Utilizatorul creat.
     */
    @PostMapping
    public User createUser(@RequestBody User user) {
        logger.info("Creating new user with username: {}", user.getUsername());
        return userService.createUser(user);
    }

    /**
     * Endpoint pentru a actualiza un utilizator existent.
     * 
     * @param id          ID-ul utilizatorului ce trebuie actualizat.
     * @param userDetails Detaliile actualizate ale utilizatorului.
     * @return Utilizatorul actualizat.
     */
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        logger.info("Updating user with ID: {}", id);
        return userService.updateUser(id, userDetails);
    }

    /**
     * Endpoint pentru a șterge un utilizator după ID.
     * 
     * @param id ID-ul utilizatorului ce trebuie șters.
     */
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        logger.info("Deleting user with ID: {}", id);
        userService.deleteUser(id);
    }
}
