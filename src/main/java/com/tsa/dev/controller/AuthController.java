package com.tsa.dev.controller;

import com.tsa.dev.requestDTO.AuthRequest;
import com.tsa.dev.requestDTO.UserRegistrationRequestDTO;
import com.tsa.dev.responseDTO.AuthResponse;
import com.tsa.dev.responseDTO.UserResponseDTO;
import com.tsa.dev.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserRegistrationRequestDTO request) {
        log.info("Attempting signup for: {}", request.getUsername());
        UserResponseDTO response = userService.signUp(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@Valid @RequestBody AuthRequest request) {
        log.info("Attempting login for: {}", request.getUsernameOrEmail());
        AuthResponse response = userService.login(request);
        log.info("Login successful for: {}", request.getUsernameOrEmail());
//        System.out.println(new BCryptPasswordEncoder().encode("admin123"));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            System.out.println("User authorities: " + auth.getAuthorities());
        }

        return ResponseEntity.ok(response);
    }
}
