package com.hogar360.houses.houses.application.services;

import com.hogar360.houses.houses.application.dto.request.SaveCategoryRequest;
import com.hogar360.houses.houses.application.dto.response.SaveCategoryResponse;

public interface CategoryService {
    SaveCategoryResponse save(SaveCategoryRequest request);
}
