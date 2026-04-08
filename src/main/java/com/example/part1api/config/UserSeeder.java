package com.example.part1api.config;

import com.example.part1api.model.User;
import com.example.part1api.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserSeeder {

    @Bean
    CommandLineRunner seedUsers(UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {
            if (userRepository.findByUsername("hr@safedispatch.com") == null) {
                User hr = new User();
                hr.setUsername("hr@safedispatch.com");
                hr.setPassword(encoder.encode("password"));
                hr.setRole("HR");
                userRepository.save(hr);
                System.out.println("HR user created");
            }
            if (userRepository.findByUsername("manager@safedispatch.com") == null) {
                User manager = new User();
                manager.setUsername("manager@safedispatch.com");
                manager.setPassword(encoder.encode("password"));
                manager.setRole("MANAGER");
                userRepository.save(manager);
                System.out.println("Manager user created");
            }
        };
    }
}