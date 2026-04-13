package com.example.part1api.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.Customizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configures security for the HQ Administration API.
 * Enforces RBAC rules and HTTP Basic authentication.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disabled as this is a stateless REST API
                .authorizeHttpRequests(auth -> auth
                        // MANAGER can only read data
                        .requestMatchers(HttpMethod.GET, "/**").hasAnyRole("HR", "MANAGER")
                        // HR has full access to create, update, delete
                        .requestMatchers(HttpMethod.POST, "/**").hasRole("HR")
                        .requestMatchers(HttpMethod.PUT, "/**").hasRole("HR")
                        .requestMatchers(HttpMethod.DELETE, "/**").hasRole("HR")
                        // All other requests must be authenticated
                        .anyRequest().authenticated()
                )
                // Use HTTP Basic Auth - credentials sent via Authorization header
                .httpBasic(Customizer.withDefaults())
                .exceptionHandling(e -> e
                        // Return 401 for unauthenticated requests instead of redirecting to login page
                        .authenticationEntryPoint((req, res, ex) ->
                                res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorised"))
                        // Return 403 for authenticated users who lack the required role
                        .accessDeniedHandler((req, res, ex) ->
                                res.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden"))
                )
                .userDetailsService(userDetailsService);

        return http.build();
    }

    @Bean
    // BCrypt password encoder - used to hash passwords before storing in the database.
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}