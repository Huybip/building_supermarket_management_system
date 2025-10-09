package com.example.servingwebcontent.config;

import com.example.servingwebcontent.model.Role;
import com.example.servingwebcontent.model.User;
import com.example.servingwebcontent.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User u = new User();
                u.setUsername("admin");
                u.setPassword(encoder.encode("Admin123!"));
                u.setRole(Role.ADMIN);
                u.setFullName("Administrator");
                u.setEnabled(true);
                userRepository.save(u);
            }
        };
    }
}
