package com.hogar360.houses.houses.application.dto.response;

import com.hogar360.houses.houses.domain.utils.PublicationStatus;

public record HouseSimpleResponse(
        Long id,
        String name,
        LocationResponse location,
        PublicationStatus status,
        Long publisherId
) {
}
