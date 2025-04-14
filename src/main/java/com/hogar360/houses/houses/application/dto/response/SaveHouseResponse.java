package com.hogar360.houses.houses.application.dto.response;

import java.time.LocalDateTime;

public record SaveHouseResponse(
        String message,
        LocalDateTime time
) {}
