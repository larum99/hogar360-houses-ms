package com.hogar360.houses.houses.infrastructure.mappers;

import com.hogar360.houses.houses.domain.model.HouseModel;
import com.hogar360.houses.houses.infrastructure.entities.HouseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryEntityMapper.class, LocationEntityMapper.class})
public interface HouseEntityMapper {

    @Mapping(source = "category", target = "category")
    @Mapping(source = "location", target = "location")
    @Mapping(source = "publisherId", target = "publisherId")
    HouseModel entityToModel(HouseEntity houseEntity);

    @Mapping(source = "category", target = "category")
    @Mapping(source = "location", target = "location")
    @Mapping(source = "publisherId", target = "publisherId")
    HouseEntity modelToEntity(HouseModel houseModel);

    List<HouseModel> entityListToModelList(List<HouseEntity> entities);
}
