package com.hogar360.houses.houses.domain.ports.out;

import com.hogar360.houses.houses.domain.model.CategoryModel;
import com.hogar360.houses.houses.domain.utils.PageResult;

import java.util.Optional;

public interface CategoryPersistencePort {
    void save(CategoryModel categoryModel);
    Optional<CategoryModel> getCategoryByName(String categoryName);
    PageResult<CategoryModel> listCategories(int page, int size, boolean orderAsc);
    Optional<CategoryModel> findById(Long id);
}
