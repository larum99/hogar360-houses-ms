package com.hogar360.houses.houses.application.mappers;

import com.hogar360.houses.houses.application.dto.response.DepartmentResponse;
import com.hogar360.houses.houses.domain.model.DepartmentModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DepartmentDtoMapper {

    DepartmentResponse modelToResponse(DepartmentModel departmentModel);

    List<DepartmentResponse> modelToResponseList(List<DepartmentModel> departmentModels);
}
