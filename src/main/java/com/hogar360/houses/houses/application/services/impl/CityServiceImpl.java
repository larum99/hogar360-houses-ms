package com.hogar360.houses.houses.application.services.impl;

import com.hogar360.houses.houses.application.dto.response.CityResponse;
import com.hogar360.houses.houses.application.mappers.CityDtoMapper;
import com.hogar360.houses.houses.application.services.CityService;
import com.hogar360.houses.houses.domain.model.CityModel;
import com.hogar360.houses.houses.domain.ports.in.CityServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityServicePort cityServicePort;
    private final CityDtoMapper cityDtoMapper;

    @Override
    public List<CityResponse> getCitiesByDepartmentId(Long departmentId) {
        List<CityModel> cityModels = cityServicePort.getCitiesByDepartment(departmentId);
        return cityDtoMapper.modelToResponseList(cityModels);
    }
}
