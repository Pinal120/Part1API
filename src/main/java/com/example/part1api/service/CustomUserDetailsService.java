package com.example.part1api.service;

import com.example.part1api.model.User;
import com.example.part1api.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

/**
 * Loads user credentials dynamically from MySQL at login time.
 * Spring Security calls loadUserByUsername() to authenticate requests.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Look up the user in the database by username
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("User not found");
        // Build a Spring Security UserDetails object with username, hashed password, and role
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole()) // Spring Security will prefix with "ROLE_" automatically
                .build();
    }
}