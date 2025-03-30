package com.hogar360.houses.houses.domain.ports.in;

import com.hogar360.houses.houses.domain.model.CategoryModel;

public interface CategoryServicePort {
    void save(CategoryModel categoryModel);
}
