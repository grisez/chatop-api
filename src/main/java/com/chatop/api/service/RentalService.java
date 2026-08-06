package com.chatop.api.service;

import com.chatop.api.dto.MessageResponse;
import com.chatop.api.dto.RentalCreateRequest;
import com.chatop.api.dto.RentalResponse;
import com.chatop.api.dto.RentalUpdateRequest;
import com.chatop.api.dto.RentalsResponse;

public interface RentalService {

    /**
     * Returns every rental listed on the platform.
     */
    RentalsResponse getAll();

    /**
     * Returns the detail of a single rental.
     */
    RentalResponse getById(Integer id);

    /**
     * Creates a new rental owned by the given user, storing the uploaded picture on disk.
     */
    MessageResponse create(RentalCreateRequest request, String ownerEmail);

    /**
     * Updates an existing rental. Only the owner of the rental may perform this action.
     */
    MessageResponse update(Integer id, RentalUpdateRequest request, String requesterEmail);
}
