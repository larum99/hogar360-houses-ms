package com.hogar360.houses.houses.infrastructure.mappers;

import com.hogar360.houses.houses.domain.model.DepartmentModel;
import com.hogar360.houses.houses.infrastructure.entities.DepartmentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentEntityMapper {
    DepartmentEntity modelToEntity(DepartmentModel departmentModel);
    DepartmentModel entityToModel(DepartmentEntity departmentEntity);
}
