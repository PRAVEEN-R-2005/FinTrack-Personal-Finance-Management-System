package com.project.finance.controller;

import com.project.finance.dto.RegisterRequest;
import com.project.finance.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(
            @Valid @RequestBody RegisterRequest registerRequest) {

        String response = userService.registerUser(registerRequest);

        return ResponseEntity.ok(response);
    }
}