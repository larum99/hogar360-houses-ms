package com.hogar360.houses.houses.application.services;

import com.hogar360.houses.houses.application.dto.response.DepartmentResponse;

import java.util.List;

public interface DepartmentService {
    List<DepartmentResponse> getAllDepartments();
}
