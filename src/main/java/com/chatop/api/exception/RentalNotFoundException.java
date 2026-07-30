package com.chatop.api.exception;

public class RentalNotFoundException extends RuntimeException {

    public RentalNotFoundException(Integer id) {
        super("No rental found with id: " + id);
    }
}
