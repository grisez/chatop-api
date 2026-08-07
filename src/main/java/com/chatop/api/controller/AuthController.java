package com.chatop.api.controller;

import com.chatop.api.config.OpenApiConfig;
import com.chatop.api.dto.AuthResponse;
import com.chatop.api.dto.LoginRequest;
import com.chatop.api.dto.RegisterRequest;
import com.chatop.api.dto.UserResponse;
import com.chatop.api.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Creates a new user account and returns an access token.
     */
    @Operation(summary = "Creates a new user account and returns an access token.")
    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    /**
     * Authenticates a user and returns an access token.
     */
    @Operation(summary = "Authenticates a user and returns an access token.")
    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    /**
     * Returns the profile of the currently authenticated user.
     */
    @Operation(summary = "Returns the profile of the currently authenticated user.")
    @GetMapping("/me")
    @SecurityRequirement(name = OpenApiConfig.BEARER_SCHEME)
    public UserResponse me(Authentication authentication) {
        return authService.getCurrentUser(authentication.getName());
    }
}
