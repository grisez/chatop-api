package com.chatop.api.mapper;

import com.chatop.api.dto.RentalResponse;
import com.chatop.api.entity.Rental;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RentalMapper {

    @Mapping(target = "ownerId", source = "owner.id")
    RentalResponse toResponse(Rental rental);
}
