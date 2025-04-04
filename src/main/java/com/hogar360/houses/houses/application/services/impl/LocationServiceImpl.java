package com.hogar360.houses.houses.application.services.impl;

import com.hogar360.houses.commons.configurations.utils.Constants;
import com.hogar360.houses.houses.application.dto.request.SaveLocationRequest;
import com.hogar360.houses.houses.application.dto.response.LocationResponse;
import com.hogar360.houses.houses.application.dto.response.PagedLocationResponse;
import com.hogar360.houses.houses.application.dto.response.SaveLocationResponse;
import com.hogar360.houses.houses.application.services.LocationService;
import com.hogar360.houses.houses.domain.model.LocationModel;
import com.hogar360.houses.houses.domain.utils.PageResult;
import com.hogar360.houses.houses.domain.ports.in.LocationServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationServicePort locationServicePort;

    @Override
    public SaveLocationResponse save(SaveLocationRequest request) {
        locationServicePort.createLocation(request.cityId(), request.sector());
        return new SaveLocationResponse(Constants.SAVE_LOCATION_RESPONSE_MESSAGE, LocalDateTime.now());
    }

    @Override
    public PagedLocationResponse searchLocations(String searchTerm, int page, int size, String sortBy, String sortDirection) {
        PageResult<LocationModel> locationPage = locationServicePort.searchLocations(searchTerm, page, size, sortBy, sortDirection);
        List<LocationResponse> locationResponses = locationPage.getContent().stream()
                .map(locationModel -> new LocationResponse(
                        locationModel.getId(),
                        locationModel.getCity().getName(),
                        locationModel.getCity().getDepartment().getName(),
                        locationModel.getSector()
                ))
                .toList();

        return new PagedLocationResponse(
                locationResponses,
                locationPage.getTotalElements(),
                locationPage.getTotalPages(),
                locationPage.getCurrentPage(),
                locationPage.getPageSize(),
                locationPage.isFirst(),
                locationPage.isLast()
        );
    }
}
