package com.chatop.api.dto;

import java.time.LocalDateTime;

public record UserResponse(
        Integer id,
        String name,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
