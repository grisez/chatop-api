package com.chatop.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters") String name,
        @NotBlank @Email String email,
        @NotBlank @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[^a-zA-Z0-9\\s]).{8,}$",
                message = "Password must be at least 8 characters long and include an uppercase letter and a special character")
        String password) {

    public RegisterRequest {
        name = name == null ? null : name.trim();
    }
}
