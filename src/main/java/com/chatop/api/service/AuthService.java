package com.chatop.api.service;

import com.chatop.api.dto.AuthResponse;
import com.chatop.api.dto.LoginRequest;
import com.chatop.api.dto.RegisterRequest;
import com.chatop.api.dto.UserResponse;

public interface AuthService {

    /**
     * Creates a new user account and returns an access token.
     */
    AuthResponse register(RegisterRequest request);

    /**
     * Authenticates a user and returns an access token.
     */
    AuthResponse login(LoginRequest request);

    /**
     * Returns the profile of the user identified by the given email.
     */
    UserResponse getCurrentUser(String email);
}
