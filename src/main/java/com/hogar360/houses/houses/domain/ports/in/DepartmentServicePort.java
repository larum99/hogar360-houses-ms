package com.hogar360.houses.houses.domain.ports.in;

import com.hogar360.houses.houses.domain.model.DepartmentModel;
import java.util.List;

public interface DepartmentServicePort {
    List<DepartmentModel> getAllDepartments();
}
