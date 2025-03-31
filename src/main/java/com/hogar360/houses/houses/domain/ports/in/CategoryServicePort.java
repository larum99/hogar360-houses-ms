package com.hogar360.houses.houses.domain.ports.in;

import com.hogar360.houses.houses.domain.model.CategoryModel;
import com.hogar360.houses.houses.domain.model.PageModel;

public interface CategoryServicePort {
    void save(CategoryModel categoryModel);
    PageModel<CategoryModel> listCategories(int page, int size, boolean orderAsc);

}
