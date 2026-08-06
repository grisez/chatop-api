package com.chatop.api.service.impl;

import com.chatop.api.dto.UserResponse;
import com.chatop.api.entity.User;
import com.chatop.api.exception.UserNotFoundException;
import com.chatop.api.mapper.UserMapper;
import com.chatop.api.repository.UserRepository;
import com.chatop.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse getById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.toResponse(user);
    }
}
