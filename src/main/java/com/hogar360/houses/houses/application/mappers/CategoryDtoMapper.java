package com.hogar360.houses.houses.application.mappers;

import com.hogar360.houses.houses.application.dto.request.SaveCategoryRequest;
import com.hogar360.houses.houses.application.dto.response.CategoryResponse;
import com.hogar360.houses.houses.domain.model.CategoryModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryDtoMapper {
    CategoryModel requestToModel(SaveCategoryRequest saveCategoryRequest);
    CategoryResponse modelToResponse(CategoryModel categoryModel);
    List<CategoryResponse> modelToResponseList(List<CategoryModel> categoryModels);
}
