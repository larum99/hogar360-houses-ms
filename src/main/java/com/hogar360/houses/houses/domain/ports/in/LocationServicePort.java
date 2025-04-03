package com.hogar360.houses.houses.domain.ports.in;

import com.hogar360.houses.houses.domain.model.LocationModel;
import com.hogar360.houses.houses.domain.model.PageModel;


public interface LocationServicePort {
    LocationModel createLocation(Long cityId, String sector);
    PageModel<LocationModel> searchLocations(String searchTerm, int page, int size, String sortBy, String sortDirection);
}
