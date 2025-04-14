package com.hogar360.houses.houses.domain.ports.out;

import com.hogar360.houses.houses.domain.model.LocationModel;
import com.hogar360.houses.houses.domain.utils.PageResult;

import java.util.Optional;

public interface LocationPersistencePort {
    LocationModel save(LocationModel locationModel);
    PageResult<LocationModel> searchLocations(String searchTerm, int page, int size, String sortBy, String sortDirection);
    Optional<LocationModel> findById(Long id);
}
