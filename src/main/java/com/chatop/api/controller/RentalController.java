package com.chatop.api.controller;

import com.chatop.api.config.OpenApiConfig;
import com.chatop.api.dto.MessageResponse;
import com.chatop.api.dto.RentalCreateRequest;
import com.chatop.api.dto.RentalResponse;
import com.chatop.api.dto.RentalUpdateRequest;
import com.chatop.api.dto.RentalsResponse;
import com.chatop.api.service.RentalService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
@SecurityRequirement(name = OpenApiConfig.BEARER_SCHEME)
public class RentalController {

    private final RentalService rentalService;

    /**
     * Trims every String field before validation/persistence: avoids storing leading/trailing
     * spaces, and keeps @Size length checks accurate instead of counting padding as content.
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

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
    public MessageResponse create(@Valid @ModelAttribute RentalCreateRequest request,
                                  Authentication authentication) {
        return rentalService.create(request, authentication.getName());
    }

    /**
     * Updates an existing rental. Only the owner of the rental may perform this action.
     */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MessageResponse update(@PathVariable Integer id,
                                   @Valid @ModelAttribute RentalUpdateRequest request,
                                   Authentication authentication) {
        return rentalService.update(id, request, authentication.getName());
    }
}
