package com.hogar360.houses.houses.infrastructure.endpoints.rest;

import com.hogar360.houses.houses.application.dto.response.CityResponse;
import com.hogar360.houses.houses.application.services.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping("/api/v1/city/department/{departmentId}")
    public List<CityResponse> getCitiesByDepartmentId(@PathVariable Long departmentId) {
        return cityService.getCitiesByDepartmentId(departmentId);
    }
}
