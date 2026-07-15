package com.hogar360.houses.houses.application.dto.response;

public record HouseSimpleResponse(
        Long id,
        String name,
        LocationResponse location
) {
}
