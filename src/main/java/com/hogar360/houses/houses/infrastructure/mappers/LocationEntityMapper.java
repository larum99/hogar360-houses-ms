package com.hogar360.houses.houses.infrastructure.mappers;

import com.hogar360.houses.houses.domain.model.LocationModel;
import com.hogar360.houses.houses.infrastructure.entities.LocationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CityEntityMapper.class})
public interface LocationEntityMapper {
    @Mapping(source = "city", target = "city")
    LocationEntity modelToEntity(LocationModel locationModel);
    LocationModel entityToModel(LocationEntity locationEntity);
    List<LocationModel> entityToModelList(List<LocationEntity> entities);
}
