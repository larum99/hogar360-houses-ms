package com.hogar360.houses.houses.infraestructure.mappers;

import com.hogar360.houses.houses.domain.model.LocationModel;
import com.hogar360.houses.houses.infraestructure.entities.LocationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CityEntityMapper.class})
public interface LocationEntityMapper {
    @Mapping(source = "city", target = "city")
    LocationEntity modelToEntity(LocationModel locationModel);
    LocationModel entityToModel(LocationEntity locationEntity);
}
