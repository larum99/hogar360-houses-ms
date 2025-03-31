package com.hogar360.houses.houses.application.services.impl;

import com.hogar360.houses.commons.configurations.utils.Constants;
import com.hogar360.houses.houses.application.dto.request.ListCategoriesRequest;
import com.hogar360.houses.houses.application.dto.request.SaveCategoryRequest;
import com.hogar360.houses.houses.application.dto.response.CategoryResponse;
import com.hogar360.houses.houses.application.dto.response.PagedCategoryResponse;
import com.hogar360.houses.houses.application.dto.response.SaveCategoryResponse;
import com.hogar360.houses.houses.application.mappers.CategoryDtoMapper;
import com.hogar360.houses.houses.application.services.CategoryService;
import com.hogar360.houses.houses.domain.model.CategoryModel;
import com.hogar360.houses.houses.domain.model.PageModel;
import com.hogar360.houses.houses.domain.ports.in.CategoryServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryServicePort categoryServicePort;
    private final CategoryDtoMapper categoryDtoMapper;

    @Override
    public SaveCategoryResponse save(SaveCategoryRequest request) {
        categoryServicePort.save(categoryDtoMapper.requestToModel(request));
        return new SaveCategoryResponse(Constants.SAVE_CATEGORY_RESPONSE_MESSAGE, LocalDateTime.now());
    }

    @Override
    public PagedCategoryResponse listCategories(ListCategoriesRequest request) {
        PageModel<CategoryModel> categoryPage = categoryServicePort.listCategories(request.page(), request.size(), request.orderAsc());
        List<CategoryResponse> categoryResponses = categoryPage.getContent().stream()
                .map(categoryDtoMapper::modelToResponse)
                .collect(Collectors.toList());

        return new PagedCategoryResponse(
                categoryResponses,
                categoryPage.getTotalElements(),
                categoryPage.getTotalPages(),
                categoryPage.getCurrentPage(),
                categoryPage.getPageSize(),
                categoryPage.isFirst(),
                categoryPage.isLast()
        );
    }
}
