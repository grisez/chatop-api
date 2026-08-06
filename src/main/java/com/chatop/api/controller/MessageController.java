package com.chatop.api.controller;

import com.chatop.api.dto.MessageCreateRequest;
import com.chatop.api.dto.MessageResponse;
import com.chatop.api.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    /**
     * Sends a message from the authenticated user about a rental.
     */
    @PostMapping
    public MessageResponse create(@RequestBody MessageCreateRequest request, Authentication authentication) {
        return messageService.create(request, authentication.getName());
    }
}
