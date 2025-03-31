package com.hogar360.houses.houses.domain.ports.out;

import com.hogar360.houses.houses.domain.model.CategoryModel;
import com.hogar360.houses.houses.domain.model.PageModel;

public interface CategoryPersistencePort {
    void save(CategoryModel categoryModel);
    CategoryModel getCategoryByName(String categoryName);
    PageModel<CategoryModel> listCategories(int page, int size, boolean orderAsc);

}
