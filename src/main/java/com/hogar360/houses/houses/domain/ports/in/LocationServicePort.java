package com.hogar360.houses.houses.domain.ports.in;

import com.hogar360.houses.houses.domain.model.LocationModel;


public interface LocationServicePort {
    LocationModel createLocation(Long cityId, String sector);

}
