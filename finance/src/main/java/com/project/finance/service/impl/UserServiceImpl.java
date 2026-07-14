package com.project.finance.service.impl;

import com.project.finance.dto.AuthResponse;
import com.project.finance.dto.loginrequest;
import com.project.finance.dto.RegisterRequest;
import com.project.finance.entity.user;
import com.project.finance.repository.UserRepository;
import com.project.finance.security.JwtService;
import com.project.finance.service.UserService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public UserServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public String registerUser(RegisterRequest registerRequest) {

        if (userRepository.existsByEmail(registerRequest.getEmail())) {

            return "Email already exists";
        }

        user user = new user();

        user.setName(registerRequest.getName());

        user.setEmail(registerRequest.getEmail());

        user.setPassword(
                passwordEncoder.encode(registerRequest.getPassword())
        );

        userRepository.save(user);

        return "User registered successfully";
    }

    @Override
    public AuthResponse loginUser(loginrequest loginRequest) {

        user user = userRepository
                .findByEmail(loginRequest.getEmail())
                .orElse(null);

        if (user == null) {

            throw new RuntimeException(
                    "Invalid email or password"
            );
        }

        boolean passwordMatches =
                passwordEncoder.matches(
                        loginRequest.getPassword(),
                        user.getPassword()
                );

        if (!passwordMatches) {

            throw new RuntimeException(
                    "Invalid email or password"
            );
        }

        String token = jwtService.generateToken(
                user.getEmail()
        );

        return new AuthResponse(token);
    }
}