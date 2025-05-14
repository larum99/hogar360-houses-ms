package com.hogar360.houses.houses.domain.usecases;

import com.hogar360.houses.houses.domain.model.CityModel;
import com.hogar360.houses.houses.domain.ports.in.CityServicePort;
import com.hogar360.houses.houses.domain.ports.out.CityPersistencePort;

import java.util.List;

public class CityUseCase implements CityServicePort {

    private final CityPersistencePort cityPersistencePort;

    public CityUseCase(CityPersistencePort cityPersistencePort) {
        this.cityPersistencePort = cityPersistencePort;
    }

    @Override
    public List<CityModel> getCitiesByDepartment(Long departmentId) {
        return cityPersistencePort.findCitiesByDepartmentId(departmentId);
    }
}

