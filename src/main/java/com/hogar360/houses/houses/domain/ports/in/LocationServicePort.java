package com.hogar360.houses.houses.domain.ports.in;

import com.hogar360.houses.houses.domain.model.LocationModel;
import com.hogar360.houses.houses.domain.utils.PageResult;

import java.util.List;


public interface LocationServicePort {
    LocationModel createLocation(Long cityId, String sector, String role);
    PageResult<LocationModel> searchLocations(String searchTerm, int page, int size, String sortBy, String sortDirection);
    List<LocationModel> findByCityId(Long cityId);
}
