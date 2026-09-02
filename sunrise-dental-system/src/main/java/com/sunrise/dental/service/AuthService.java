package com.sunrise.dental.service;

import com.sunrise.dental.dto.LoginRequest;
import com.sunrise.dental.model.User;
import com.sunrise.dental.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Handles Task 1: User Authentication (Login).
 * Delegates the actual credential check to Spring Security's
 * AuthenticationManager (which in turn uses StaffUserDetailsService +
 * BCryptPasswordEncoder), then stores the authenticated principal in the
 * session-backed SecurityContext so subsequent requests are recognised as
 * "logged in".
 */
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    public Map<String, Object> login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalStateException("Authenticated user vanished from the database"));

        return Map.of(
                "username", user.getUsername(),
                "fullName", user.getFullName(),
                "role", user.getRole().name(),
                "message", "Login successful. Welcome, " + user.getFullName() + "!"
        );
    }

    public void logout() {
        SecurityContextHolder.clearContext();
    }
}
