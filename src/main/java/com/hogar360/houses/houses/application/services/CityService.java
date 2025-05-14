package com.hogar360.houses.houses.application.services;

import com.hogar360.houses.houses.application.dto.response.CityResponse;

import java.util.List;

public interface CityService {
    List<CityResponse> getCitiesByDepartmentId(Long departmentId);
}
