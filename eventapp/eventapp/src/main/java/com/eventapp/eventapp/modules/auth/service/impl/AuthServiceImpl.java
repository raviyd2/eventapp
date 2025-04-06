package com.eventapp.eventapp.modules.auth.service.impl;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eventapp.eventapp.config.JwtUtil;
import com.eventapp.eventapp.modules.auth.dto.AuthRequest;
import com.eventapp.eventapp.modules.auth.dto.AuthResponse;
import com.eventapp.eventapp.modules.auth.exception.AuthException;
import com.eventapp.eventapp.modules.auth.model.User;
import com.eventapp.eventapp.modules.auth.repository.UserRepository;
import com.eventapp.eventapp.modules.auth.service.AuthService;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(AuthRequest request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new AuthException("Email already in use");
        }

        // Validate and assign role (default to USER if not provided)
        User.Role role;
        try {
            role = (request.getRole() != null) 
                ? User.Role.valueOf(request.getRole().toUpperCase()) 
                : User.Role.USER; // Default role
        } catch (IllegalArgumentException e) {
            throw new AuthException("Invalid role: " + request.getRole());
        }

        // Create user with the assigned role
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setRole(role); // Dynamic role assignment

        User savedUser = userRepository.save(user);

        // Generate token and build response
        String token = jwtUtil.generateToken(user);
        return AuthResponse.builder()
                .token(token)
                .email(savedUser.getEmail())
                .name(savedUser.getName())
                .role(savedUser.getRole().name())
                .build();
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        try {
            // Authenticate user
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
                )
            );

            // Get user details
            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            // Generate token
            String token = jwtUtil.generateToken(user);

            // Build response
            AuthResponse response = new AuthResponse();
            response.setToken(token);
            response.setEmail(user.getEmail());
            response.setName(user.getName());
            response.setRole(user.getRole().name());

            return response;

        } catch (Exception e) {
            throw new AuthException("Invalid email/password");
        }
    }

	@Override
	public AuthResponse refreshToken(String refreshToken) {
		// TODO Auto-generated method stub
		return null;
	}
}