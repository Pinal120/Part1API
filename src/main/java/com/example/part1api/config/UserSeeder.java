package com.example.part1api.config;

import com.example.part1api.model.User;
import com.example.part1api.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Seeds default HR and MANAGER users into the database on startup.
 * Only creates them if they don't already exist, so it's safe to run repeatedly.
 */
@Configuration
public class UserSeeder {

    @Bean
    CommandLineRunner seedUsers(UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {
            // Create HR user if not already in the database
            if (userRepository.findByUsername("hr@safedispatch.com") == null) {
                User hr = new User();
                hr.setUsername("hr@safedispatch.com");
                hr.setPassword(encoder.encode("password"));  // BCrypt hashed
                hr.setRole("HR");
                userRepository.save(hr);
                System.out.println("HR user created");
            }
            // Create MANAGER user if not already in the database
            if (userRepository.findByUsername("manager@safedispatch.com") == null) {
                User manager = new User();
                manager.setUsername("manager@safedispatch.com");
                manager.setPassword(encoder.encode("password")); // BCrypt hashed
                manager.setRole("MANAGER");
                userRepository.save(manager);
                System.out.println("Manager user created");
            }
        };
    }
}