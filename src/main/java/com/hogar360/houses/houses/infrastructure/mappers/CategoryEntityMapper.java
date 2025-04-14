package com.hogar360.houses.houses.infrastructure.mappers;

import com.hogar360.houses.houses.domain.model.CategoryModel;
import com.hogar360.houses.houses.infrastructure.entities.CategoryEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryEntityMapper {
    CategoryModel entityToModel(CategoryEntity categoryEntity);
    CategoryEntity modelToEntity(CategoryModel categoryModel);
    List<CategoryModel> entityToModelList(List<CategoryEntity> categoryEntities);
}
