package com.hogar360.houses.houses.domain.ports.out;

import com.hogar360.houses.houses.domain.model.LocationModel;
import com.hogar360.houses.houses.domain.model.PageModel;

public interface LocationPersistencePort {
    LocationModel save(LocationModel locationModel);
    LocationModel getById(Long id);
    PageModel<LocationModel> searchLocations(String searchTerm, int page, int size, String sortBy, String sortDirection);
}
