package com.chatop.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RentalResponse(
        Integer id,
        String name,
        BigDecimal surface,
        BigDecimal price,
        String picture,
        String description,
        @JsonProperty("owner_id") Integer ownerId,
        @JsonProperty("created_at") LocalDateTime createdAt,
        @JsonProperty("updated_at") LocalDateTime updatedAt) {
}
