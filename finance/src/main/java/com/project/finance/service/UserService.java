package com.project.finance.service;

import com.project.finance.dto.AuthResponse;
import com.project.finance.dto.loginrequest;
import com.project.finance.dto.RegisterRequest;

public interface UserService {

    String registerUser(RegisterRequest registerRequest);

    AuthResponse loginUser(loginrequest loginRequest);
}