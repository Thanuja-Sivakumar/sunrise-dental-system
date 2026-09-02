package com.sunrise.dental.config;

import com.sunrise.dental.service.StaffUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Secures the system so that "only authorized staff can use the system"
 * (Task 1: User Authentication requirement).
 *
 * - Static assets and the login endpoint are public.
 * - Every other API endpoint requires a valid authenticated session.
 * - Passwords are stored using BCrypt, never in plain text.
 * - Session-based auth is used (not JWT) to keep the coursework scope
 *   manageable, while still demonstrating a real Spring Security pipeline.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final StaffUserDetailsService userDetailsService;

    public SecurityConfig(StaffUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // simplifies calling the JSON REST API from the SPA frontend
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/index.html", "/login.html", "/css/**", "/js/**",
                        "/api/auth/login", "/h2-console/**").permitAll()
                .anyRequest().authenticated()
            )
            .headers(headers -> headers.frameOptions(frame -> frame.disable())) // allow H2 console frames
            .httpBasic(httpBasic -> {}) // enables simple authenticated calls in addition to session auth
            .formLogin(form -> form.disable())
            .logout(logout -> logout.logoutUrl("/api/auth/logout"));

        return http.build();
    }
}
