package com.hogar360.houses.houses.domain.ports.out;

import com.hogar360.houses.houses.domain.model.DepartmentModel;

public interface DepartmentPersistencePort {
        DepartmentModel getDepartmentByName(String name);
}
