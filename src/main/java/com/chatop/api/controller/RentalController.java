package com.chatop.api.controller;

import com.chatop.api.dto.MessageResponse;
import com.chatop.api.dto.RentalCreateRequest;
import com.chatop.api.dto.RentalResponse;
import com.chatop.api.dto.RentalUpdateRequest;
import com.chatop.api.dto.RentalsResponse;
import com.chatop.api.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    /**
     * Returns every rental listed on the platform.
     */
    @GetMapping
    public RentalsResponse getAll() {
        return rentalService.getAll();
    }

    /**
     * Returns the detail of a single rental.
     */
    @GetMapping("/{id}")
    public RentalResponse getById(@PathVariable Integer id) {
        return rentalService.getById(id);
    }

    /**
     * Creates a new rental owned by the authenticated user, storing the uploaded picture on disk.
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MessageResponse create(@RequestParam String name,
                                   @RequestParam BigDecimal surface,
                                   @RequestParam BigDecimal price,
                                   @RequestParam String description,
                                   @RequestParam MultipartFile picture,
                                   Authentication authentication) {
        RentalCreateRequest request = new RentalCreateRequest(name, surface, price, description, picture);
        return rentalService.create(request, authentication.getName());
    }

    /**
     * Updates an existing rental. Only the owner of the rental may perform this action.
     */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MessageResponse update(@PathVariable Integer id,
                                   @RequestParam String name,
                                   @RequestParam BigDecimal surface,
                                   @RequestParam BigDecimal price,
                                   @RequestParam String description,
                                   Authentication authentication) {
        RentalUpdateRequest request = new RentalUpdateRequest(name, surface, price, description);
        return rentalService.update(id, request, authentication.getName());
    }
}
