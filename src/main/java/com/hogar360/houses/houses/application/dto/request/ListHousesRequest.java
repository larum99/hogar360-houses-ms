package com.hogar360.houses.houses.application.dto.request;

import java.math.BigDecimal;

public record ListHousesRequest(
        String department,
        String city,
        String sector,
        String category,
        Integer bedrooms,
        Integer bathrooms,
        BigDecimal price,
        String sortBy,
        String sortDirection,
        int page,
        int size
) {}
