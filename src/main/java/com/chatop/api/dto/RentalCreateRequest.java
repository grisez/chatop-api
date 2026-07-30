package com.chatop.api.dto;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public record RentalCreateRequest(
        String name,
        BigDecimal surface,
        BigDecimal price,
        String description,
        MultipartFile picture) {
}
