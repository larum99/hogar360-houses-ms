package com.hogar360.houses.houses.domain.ports.out;

import com.hogar360.houses.houses.domain.model.DepartmentModel;
import java.util.List;

public interface DepartmentPersistencePort {
    List<DepartmentModel> findAllDepartments();
}
