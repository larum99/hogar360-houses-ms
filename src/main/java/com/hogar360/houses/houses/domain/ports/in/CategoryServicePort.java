package com.hogar360.houses.houses.domain.ports.in;

import com.hogar360.houses.houses.domain.model.CategoryModel;
import com.hogar360.houses.houses.domain.utils.PageResult;

public interface CategoryServicePort {
    void save(CategoryModel categoryModel, String token);
    PageResult<CategoryModel> listCategories(int page, int size, boolean orderAsc);

}
