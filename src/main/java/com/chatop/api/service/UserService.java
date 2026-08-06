package com.chatop.api.service;

import com.chatop.api.dto.UserResponse;

public interface UserService {

    UserResponse getById(Integer id);
}
