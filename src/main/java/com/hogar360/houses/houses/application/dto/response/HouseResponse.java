package com.hogar360.houses.houses.application.dto.response;

import com.hogar360.houses.houses.domain.utils.PublicationStatus;
import java.math.BigDecimal;
import java.time.LocalDate;

public record HouseResponse(
        Long id,
        String name,
        String description,
        CategoryResponse category,
        int bedrooms,
        int bathrooms,
        BigDecimal price,
        LocationResponse location,
        LocalDate publicationDate,
        LocalDate activePublicationDate,
        PublicationStatus status,
        Long publisherId
) {}
