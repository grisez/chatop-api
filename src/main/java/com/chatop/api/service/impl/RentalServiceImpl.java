package com.chatop.api.service.impl;

import com.chatop.api.dto.MessageResponse;
import com.chatop.api.dto.RentalCreateRequest;
import com.chatop.api.dto.RentalResponse;
import com.chatop.api.dto.RentalUpdateRequest;
import com.chatop.api.dto.RentalsResponse;
import com.chatop.api.entity.Rental;
import com.chatop.api.entity.User;
import com.chatop.api.exception.ForbiddenOperationException;
import com.chatop.api.exception.InvalidRentalDataException;
import com.chatop.api.exception.RentalNotFoundException;
import com.chatop.api.mapper.RentalMapper;
import com.chatop.api.repository.RentalRepository;
import com.chatop.api.repository.UserRepository;
import com.chatop.api.service.RentalService;
import com.chatop.api.storage.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final RentalMapper rentalMapper;
    private final FileStorageService fileStorageService;

    @Override
    public RentalsResponse getAll() {
        List<RentalResponse> rentals = rentalRepository.findAll().stream()
                .map(rentalMapper::toResponse)
                .toList();

        return new RentalsResponse(rentals);
    }

    @Override
    public RentalResponse getById(Integer id) {
        Rental rental = findRentalOrThrow(id);
        return rentalMapper.toResponse(rental);
    }

    @Override
    public MessageResponse create(RentalCreateRequest request, String ownerEmail) {
        validate(request.surface(), request.price(), request.name(), request.description());

        User owner = findUserOrThrow(ownerEmail);
        String pictureUrl = fileStorageService.store(request.picture());

        Rental rental = Rental.builder()
                .name(request.name())
                .surface(request.surface())
                .price(request.price())
                .description(request.description())
                .picture(pictureUrl)
                .owner(owner)
                .build();
        rentalRepository.save(rental);

        return new MessageResponse("Rental created !");
    }

    @Override
    public MessageResponse update(Integer id, RentalUpdateRequest request, String requesterEmail) {
        validate(request.surface(), request.price(), request.name(), request.description());

        Rental rental = findRentalOrThrow(id);

        if (!rental.getOwner().getEmail().equals(requesterEmail)) {
            throw new ForbiddenOperationException("You are not allowed to update this rental");
        }

        rental.setName(request.name());
        rental.setSurface(request.surface());
        rental.setPrice(request.price());
        rental.setDescription(request.description());
        rentalRepository.save(rental);

        return new MessageResponse("Rental updated !");
    }

    private Rental findRentalOrThrow(Integer id) {
        return rentalRepository.findById(id)
                .orElseThrow(() -> new RentalNotFoundException(id));
    }

    private User findUserOrThrow(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with email: " + email));
    }

    private void validate(BigDecimal surface, BigDecimal price, String name, String description) {
        if (surface == null || surface.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidRentalDataException("Surface must be a positive number");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidRentalDataException("Price must be a positive number");
        }
        if (!StringUtils.hasText(name)) {
            throw new InvalidRentalDataException("Name must not be blank");
        }
        if (!StringUtils.hasText(description)) {
            throw new InvalidRentalDataException("Description must not be blank");
        }
    }
}
