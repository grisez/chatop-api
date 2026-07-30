package com.chatop.api.mapper;

import com.chatop.api.dto.UserResponse;
import com.chatop.api.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponse(User user);
}
