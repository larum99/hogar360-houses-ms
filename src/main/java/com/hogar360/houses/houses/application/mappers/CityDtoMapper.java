package com.hogar360.houses.houses.application.mappers;

import com.hogar360.houses.houses.application.dto.response.CityResponse;
import com.hogar360.houses.houses.domain.model.CityModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CityDtoMapper {

    @Mapping(source = "department.id", target = "departmentId")
    CityResponse modelToResponse(CityModel cityModel);

    List<CityResponse> modelToResponseList(List<CityModel> cityModels);
}
