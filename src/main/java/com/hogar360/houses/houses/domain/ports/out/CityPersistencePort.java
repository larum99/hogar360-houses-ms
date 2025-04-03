package com.hogar360.houses.houses.domain.ports.out;

import com.hogar360.houses.houses.domain.model.CityModel;
import com.hogar360.houses.houses.domain.model.DepartmentModel;

public interface CityPersistencePort {
    CityModel getByNameAndDepartment(String name, DepartmentModel department);
    CityModel getCityById(Long id);
}
