package com.hogar360.houses.houses.application.services.impl;

import com.hogar360.houses.commons.configurations.utils.Constants;
import com.hogar360.houses.houses.application.dto.request.SaveLocationRequest;
import com.hogar360.houses.houses.application.dto.response.SaveLocationResponse;
import com.hogar360.houses.houses.application.services.LocationService;
import com.hogar360.houses.houses.domain.ports.in.LocationServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationServicePort locationServicePort;

    @Override
    public SaveLocationResponse save(SaveLocationRequest request) {
        locationServicePort.createLocation(request.cityId(), request.sector());
        return new SaveLocationResponse(Constants.SAVE_LOCATION_RESPONSE_MESSAGE, LocalDateTime.now());
    }
}
