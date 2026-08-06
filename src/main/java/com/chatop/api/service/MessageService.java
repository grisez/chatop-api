package com.chatop.api.service;

import com.chatop.api.dto.MessageCreateRequest;
import com.chatop.api.dto.MessageResponse;

public interface MessageService {

    /**
     * Sends a message from the given user about a rental.
     */
    MessageResponse create(MessageCreateRequest request, String senderEmail);
}
