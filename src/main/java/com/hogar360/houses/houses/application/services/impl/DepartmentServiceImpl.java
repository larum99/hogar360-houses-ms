package com.hogar360.houses.houses.application.services.impl;

import com.hogar360.houses.houses.application.dto.response.DepartmentResponse;
import com.hogar360.houses.houses.application.mappers.DepartmentDtoMapper;
import com.hogar360.houses.houses.application.services.DepartmentService;
import com.hogar360.houses.houses.domain.model.DepartmentModel;
import com.hogar360.houses.houses.domain.ports.in.DepartmentServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentServicePort departmentServicePort;
    private final DepartmentDtoMapper departmentDtoMapper;

    @Override
    public List<DepartmentResponse> getAllDepartments() {
        List<DepartmentModel> departmentModels = departmentServicePort.getAllDepartments();
        return departmentDtoMapper.modelToResponseList(departmentModels);
    }
}
