package com.chatop.api.service.impl;

import com.chatop.api.dto.MessageCreateRequest;
import com.chatop.api.dto.MessageResponse;
import com.chatop.api.entity.Message;
import com.chatop.api.entity.Rental;
import com.chatop.api.entity.User;
import com.chatop.api.exception.RentalNotFoundException;
import com.chatop.api.repository.MessageRepository;
import com.chatop.api.repository.RentalRepository;
import com.chatop.api.repository.UserRepository;
import com.chatop.api.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;

    @Override
    public MessageResponse create(MessageCreateRequest request, String senderEmail) {
        Rental rental = findRentalOrThrow(request.rentalId());
        User sender = findUserOrThrow(senderEmail);

        Message message = Message.builder()
                .rental(rental)
                .user(sender)
                .message(request.message())
                .build();
        messageRepository.save(message);

        return new MessageResponse("Message send with success");
    }

    private Rental findRentalOrThrow(Integer rentalId) {
        return rentalRepository.findById(rentalId)
                .orElseThrow(() -> new RentalNotFoundException(rentalId));
    }

    private User findUserOrThrow(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with email: " + email));
    }
}
