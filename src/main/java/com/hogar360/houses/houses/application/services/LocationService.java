package com.hogar360.houses.houses.application.services;

import com.hogar360.houses.houses.application.dto.request.SaveLocationRequest;
import com.hogar360.houses.houses.application.dto.response.PagedLocationResponse;
import com.hogar360.houses.houses.application.dto.response.SaveLocationResponse;

public interface LocationService {
    SaveLocationResponse save(SaveLocationRequest request);
    PagedLocationResponse searchLocations(String searchTerm, int page, int size, String sortBy, String sortDirection);
}
