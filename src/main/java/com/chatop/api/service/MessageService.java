package com.chatop.api.service;

import com.chatop.api.dto.MessageCreateRequest;
import com.chatop.api.dto.MessageResponse;

public interface MessageService {

    MessageResponse create(MessageCreateRequest request, String senderEmail);
}
