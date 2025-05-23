package com.hogar360.houses.houses.domain.usecases;

import com.hogar360.houses.houses.domain.model.DepartmentModel;
import com.hogar360.houses.houses.domain.ports.in.DepartmentServicePort;
import com.hogar360.houses.houses.domain.ports.out.DepartmentPersistencePort;

import java.util.List;

public class DepartmentUseCase implements DepartmentServicePort {

    private final DepartmentPersistencePort departmentPersistencePort;

    public DepartmentUseCase(DepartmentPersistencePort departmentPersistencePort) {
        this.departmentPersistencePort = departmentPersistencePort;
    }

    @Override
    public List<DepartmentModel> getAllDepartments() {
        return departmentPersistencePort.findAllDepartments();
    }
}
