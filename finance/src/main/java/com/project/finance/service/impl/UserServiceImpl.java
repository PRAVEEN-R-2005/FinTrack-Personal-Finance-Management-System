package com.project.finance.service.impl;

import com.project.finance.dto.RegisterRequest;
import com.project.finance.entity.user;
import com.project.finance.repository.UserRepository;
import com.project.finance.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String registerUser(RegisterRequest registerRequest) {

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return "Email already exists";
        }

        user user = new user();

        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());

        userRepository.save(user);

        return "User registered successfully";
    }
}