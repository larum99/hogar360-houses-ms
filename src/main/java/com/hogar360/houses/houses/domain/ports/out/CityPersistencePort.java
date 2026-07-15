package com.hogar360.houses.houses.domain.ports.out;

import com.hogar360.houses.houses.domain.model.CityModel;

import java.util.List;
import java.util.Optional;

public interface CityPersistencePort {
    Optional<CityModel> getCityById(Long cityId);
    List<CityModel> findCitiesByDepartmentId(Long departmentId);
}
