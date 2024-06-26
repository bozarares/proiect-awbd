package com.example.api.controller;

import com.example.api.dto.AuthRequest;
import com.example.api.dto.AuthResponse;
import com.example.api.entity.User;
import com.example.api.service.UserService;
import com.example.api.util.JwtUtil;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Endpoint pentru înregistrarea unui utilizator nou și generarea unui token
     * JWT.
     * 
     * @param user Obiectul User ce conține detaliile utilizatorului de înregistrat.
     * @return Un ResponseEntity ce conține token-ul JWT și detaliile
     *         utilizatorului.
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody User user) {
        logger.info("Attempting to register user with username: {}", user.getUsername());

        String rawPassword = user.getPassword();
        // Înregistrarea utilizatorului
        User registeredUser = userService.registerUser(user);

        logger.info("User registered successfully with username: {}", registeredUser.getUsername());

        // Autentificarea utilizatorului nou înregistrat
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), rawPassword));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generarea token-ului JWT
        final UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
        final String token = jwtUtil.generateToken(userDetails.getUsername());

        // Returnarea răspunsului cu token-ul JWT și detaliile utilizatorului
        return ResponseEntity.ok(new AuthResponse(token, registeredUser));
    }

    /**
     * Endpoint pentru autentificarea utilizatorului și generarea unui token JWT.
     * 
     * @param authRequest Obiectul AuthRequest ce conține credențialele
     *                    utilizatorului.
     * @return Un ResponseEntity ce conține token-ul JWT și detaliile
     *         utilizatorului.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> createAuthenticationToken(@RequestBody @Valid AuthRequest authRequest)
            throws Exception {
        logger.info("Attempting to authenticate user with username: {}", authRequest.getUsername());

        // Autentificarea utilizatorului
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generarea token-ului JWT
        final UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        final String token = jwtUtil.generateToken(userDetails.getUsername());

        // Obținerea detaliilor utilizatorului
        final User user = userService.findByUsername(authRequest.getUsername());
        logger.info("User authenticated successfully with username: {}", user.getUsername());

        // Returnarea răspunsului cu token-ul JWT și detaliile utilizatorului
        return ResponseEntity.ok(new AuthResponse(token, user));
    }

    /**
     * Endpoint pentru delogarea utilizatorului curent.
     * 
     * @return Un mesaj ce confirmă delogarea.
     */
    @GetMapping("/logout")
    public String logout() {
        logger.info("Logging out user with username: {}",
                SecurityContextHolder.getContext().getAuthentication().getName());
        SecurityContextHolder.clearContext();
        logger.info("User logged out successfully");
        return "Logged out successfully";
    }
}
