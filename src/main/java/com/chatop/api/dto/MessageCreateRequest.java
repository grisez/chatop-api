package com.chatop.api.dto;

public record MessageCreateRequest(
        Integer rentalId,
        // Sent by the front-end but ignored server-side: the sender is derived from the
        // authenticated principal, never trusted from the request body.
        Integer userId,
        String message) {
}
