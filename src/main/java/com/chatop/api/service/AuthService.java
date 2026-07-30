package com.chatop.api.service;

import com.chatop.api.dto.AuthResponse;
import com.chatop.api.dto.LoginRequest;
import com.chatop.api.dto.RegisterRequest;
import com.chatop.api.dto.UserResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    UserResponse getCurrentUser(String email);
}
