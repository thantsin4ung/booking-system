package com.tsa.dev.config;

import com.tsa.dev.model.User;
import com.tsa.dev.model.enums.Roles;
import com.tsa.dev.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        String adminEmail = "admin@example.com";
        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setFirstName("System");
            admin.setLastName("Admin");
            admin.setPhoneNumber("0932232323");
            admin.setRole(Roles.ADMIN);
            admin.setCreatedAt(Instant.now());
            admin.setUpdatedAt(Instant.now());

            userRepository.save(admin);
            System.out.println("Admin user created.");
        } else {
            System.out.println("Admin user already exists.");
        }
    }
}
