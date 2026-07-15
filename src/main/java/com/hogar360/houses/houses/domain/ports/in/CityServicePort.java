package com.hogar360.houses.houses.domain.ports.in;

import com.hogar360.houses.houses.domain.model.CityModel;
import java.util.List;

public interface CityServicePort {
    List<CityModel> getCitiesByDepartment(Long departmentId);
}
