package com.chatop.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MessageCreateRequest(
        @NotNull @JsonProperty("rental_id") Integer rentalId,
        // Sent by the front-end but ignored server-side: the sender is derived from the
        // authenticated principal, never trusted from the request body.
        @JsonProperty("user_id") Integer userId,
        @NotBlank @Size(min = 1, max = 2000, message = "Message must be between 1 and 2000 characters") String message) {

    public MessageCreateRequest {
        message = message == null ? null : message.trim();
    }
}
