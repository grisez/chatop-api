package com.chatop.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public record RentalCreateRequest(
        @NotBlank @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters") String name,
        @Positive BigDecimal surface,
        @Positive BigDecimal price,
        @NotBlank @Size(min = 10, max = 2000, message = "Description must be between 10 and 2000 characters") String description,
        @NotNull MultipartFile picture) {
}
