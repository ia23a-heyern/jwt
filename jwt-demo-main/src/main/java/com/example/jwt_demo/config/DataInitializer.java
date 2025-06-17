package com.example.jwt_demo.config;

import com.example.jwt_demo.model.User;
import com.example.jwt_demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 0) {
                User user = new User();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("password")); // Achtung: immer ENCODEN!
                userRepository.save(user);
                System.out.println("✔ Demo-User 'user' (password) angelegt.");
            } else {
                System.out.println("✔ User-Tabelle nicht leer, kein Demo-User angelegt.");
            }
        };
    }
}
