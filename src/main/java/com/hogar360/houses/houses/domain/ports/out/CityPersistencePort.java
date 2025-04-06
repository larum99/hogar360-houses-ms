package com.hogar360.houses.houses.domain.ports.out;

import com.hogar360.houses.houses.domain.model.CityModel;

public interface CityPersistencePort {
    CityModel getCityById(Long id);
}
