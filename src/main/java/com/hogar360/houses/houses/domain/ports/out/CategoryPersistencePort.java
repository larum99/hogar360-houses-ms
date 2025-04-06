package com.hogar360.houses.houses.domain.ports.out;

import com.hogar360.houses.houses.domain.model.CategoryModel;
import com.hogar360.houses.houses.domain.utils.PageResult;

public interface CategoryPersistencePort {
    void save(CategoryModel categoryModel);
    CategoryModel getCategoryByName(String categoryName);
    PageResult<CategoryModel> listCategories(int page, int size, boolean orderAsc);

}
