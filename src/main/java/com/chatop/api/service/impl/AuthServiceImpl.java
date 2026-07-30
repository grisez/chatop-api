package com.chatop.api.service.impl;

import com.chatop.api.dto.AuthResponse;
import com.chatop.api.dto.LoginRequest;
import com.chatop.api.dto.RegisterRequest;
import com.chatop.api.dto.UserResponse;
import com.chatop.api.entity.User;
import com.chatop.api.exception.EmailAlreadyUsedException;
import com.chatop.api.mapper.UserMapper;
import com.chatop.api.repository.UserRepository;
import com.chatop.api.security.JwtService;
import com.chatop.api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new EmailAlreadyUsedException(request.email());
        }

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();
        userRepository.save(user);

        return new AuthResponse(jwtService.generateToken(user.getEmail()));
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        return new AuthResponse(jwtService.generateToken(request.email()));
    }

    @Override
    public UserResponse getCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with email: " + email));

        return userMapper.toResponse(user);
    }
}
