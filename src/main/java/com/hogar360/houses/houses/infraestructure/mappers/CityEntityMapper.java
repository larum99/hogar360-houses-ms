package com.hogar360.houses.houses.infraestructure.mappers;

import com.hogar360.houses.houses.domain.model.CityModel;
import com.hogar360.houses.houses.infraestructure.entities.CityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DepartmentEntityMapper.class})
public interface CityEntityMapper {
    @Mapping(source = "department", target = "department")
    CityEntity modelToEntity(CityModel cityModel);
    CityModel entityToModel(CityEntity cityEntity);
    List<CityModel> entityToModelList(List<CityEntity> entities);
}
