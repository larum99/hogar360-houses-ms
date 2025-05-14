package com.hogar360.houses.houses.domain.ports.out;

import com.hogar360.houses.houses.domain.model.CityModel;

import java.util.List;

public interface CityPersistencePort {
    CityModel getCityById(Long id);
    List<CityModel> findCitiesByDepartmentId(Long departmentId);
}
