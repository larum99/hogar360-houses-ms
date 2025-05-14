package com.hogar360.houses.houses.infrastructure.endpoints.rest;

import com.hogar360.houses.houses.application.dto.response.DepartmentResponse;
import com.hogar360.houses.houses.application.services.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/api/v1/department")
    public List<DepartmentResponse> getAllDepartments() {
        return departmentService.getAllDepartments();
    }
}
