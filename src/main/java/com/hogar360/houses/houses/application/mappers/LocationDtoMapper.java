package com.hogar360.houses.houses.application.mappers;

import com.hogar360.houses.houses.application.dto.response.LocationResponse;
import com.hogar360.houses.houses.domain.model.LocationModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LocationDtoMapper {

    @Mapping(source = "city.name", target = "cityName")
    @Mapping(source = "city.department.name", target = "departmentName")
    LocationResponse modelToResponse(LocationModel locationModel);

    List<LocationResponse> modelToResponseList(List<LocationModel> locationModels);
}
