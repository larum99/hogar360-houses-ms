package com.hogar360.houses.houses.domain.ports.out;

import com.hogar360.houses.houses.domain.model.LocationModel;

public interface LocationPersistencePort {
    LocationModel save(LocationModel locationModel);
}
