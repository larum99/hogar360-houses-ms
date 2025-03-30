package com.hogar360.houses.houses.domain.ports.out;

import com.hogar360.houses.houses.domain.model.CategoryModel;

public interface CategoryPersistencePort {
    void save(CategoryModel categoryModel);
    CategoryModel getCategoryByName(String categoryName);
}
