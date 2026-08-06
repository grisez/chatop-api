package com.chatop.api.service;

import com.chatop.api.dto.UserResponse;

public interface UserService {

    /**
     * Returns the public profile of a user by id.
     */
    UserResponse getById(Integer id);
}
