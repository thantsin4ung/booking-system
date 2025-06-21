package com.tsa.dev.service;

import com.tsa.dev.config.JwtTokenUtil;
import com.tsa.dev.model.CustomUserDetails;
import com.tsa.dev.model.User;
import com.tsa.dev.model.enums.Roles;
import com.tsa.dev.repo.UserRepository;
import com.tsa.dev.requestDTO.AuthRequest;
import com.tsa.dev.requestDTO.UserRegistrationRequestDTO;
import com.tsa.dev.responseDTO.AuthResponse;
import com.tsa.dev.responseDTO.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByEmailOrUsername(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email or username: " + usernameOrEmail));
        return new CustomUserDetails(user);
    }

    public UserResponseDTO signUp(UserRegistrationRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail()) || userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username or Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(Roles.CLIENT);
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());

        User saved = userRepository.save(user);

        return UserResponseDTO.builder()
                .id(saved.getId())
                .username(saved.getUsername())
                .email(saved.getEmail())
                .firstName(saved.getFirstName())
                .lastName(saved.getLastName())
                .phoneNumber(saved.getPhoneNumber())
                .build();
    }

    public AuthResponse login(AuthRequest request) {
        try {
            AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();

            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsernameOrEmail(), request.getPassword())
            );

            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
            User user = userDetails.getUser();

            log.info("Login successful for: {} ({})", user.getUsername(), user.getEmail());

            String token = jwtTokenUtil.generateToken(user.getUsername(), user.getRole().name());

            return AuthResponse.builder()
                    .token(token)
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Login failed", e);
        }

    }
}