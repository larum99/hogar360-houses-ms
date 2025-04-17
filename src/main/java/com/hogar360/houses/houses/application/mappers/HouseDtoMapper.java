package com.hogar360.houses.houses.application.mappers;

import com.hogar360.houses.houses.application.dto.request.SaveHouseRequest;
import com.hogar360.houses.houses.application.dto.response.HouseResponse;
import com.hogar360.houses.houses.domain.model.HouseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HouseDtoMapper {

    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(source = "locationId", target = "location.id")
    HouseModel requestToModel(SaveHouseRequest saveHouseRequest);


    @Mapping(source = "category.name", target = "category.name")
    @Mapping(source = "location.city.name", target = "location.cityName")
    @Mapping(source = "location.city.department.name", target = "location.departmentName")
    HouseResponse modelToResponse(HouseModel houseModel);

    List<HouseResponse> modelToResponseList(List<HouseModel> houseModels);
}
