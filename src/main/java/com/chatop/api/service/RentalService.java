package com.chatop.api.service;

import com.chatop.api.dto.MessageResponse;
import com.chatop.api.dto.RentalCreateRequest;
import com.chatop.api.dto.RentalResponse;
import com.chatop.api.dto.RentalUpdateRequest;
import com.chatop.api.dto.RentalsResponse;

public interface RentalService {

    RentalsResponse getAll();

    RentalResponse getById(Integer id);

    MessageResponse create(RentalCreateRequest request, String ownerEmail);

    MessageResponse update(Integer id, RentalUpdateRequest request, String requesterEmail);
}
