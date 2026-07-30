package com.chatop.api.dto;

import java.math.BigDecimal;

public record RentalUpdateRequest(
        String name,
        BigDecimal surface,
        BigDecimal price,
        String description) {
}
