package com.chatop.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RentalResponse(
        Integer id,
        String name,
        BigDecimal surface,
        BigDecimal price,
        String picture,
        String description,
        Integer ownerId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
