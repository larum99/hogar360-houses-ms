package com.hogar360.houses.houses.application.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SaveHouseRequest(
        String name,
        String description,
        Long categoryId,
        int bedrooms,
        int bathrooms,
        BigDecimal price,
        Long locationId,
        LocalDate activePublicationDate
) {}
