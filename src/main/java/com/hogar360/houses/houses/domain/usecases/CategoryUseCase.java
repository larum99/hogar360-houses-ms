package com.hogar360.houses.houses.domain.usecases;

import com.hogar360.houses.houses.domain.exceptions.*;
import com.hogar360.houses.houses.domain.model.CategoryModel;
import com.hogar360.houses.houses.domain.utils.PageResult;
import com.hogar360.houses.houses.domain.ports.in.CategoryServicePort;
import com.hogar360.houses.houses.domain.ports.out.CategoryPersistencePort;
import com.hogar360.houses.houses.domain.utils.constants.DomainConstants;

import java.util.Objects;

public class CategoryUseCase implements CategoryServicePort {
    private final CategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(CategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void save(CategoryModel categoryModel) {
        validateCategoryName(categoryModel.getName());
        validateCategoryDescription(categoryModel.getDescription());
        checkIfCategoryAlreadyExists(categoryModel.getName());
        categoryPersistencePort.save(categoryModel);
    }

    @Override
    public PageResult<CategoryModel> listCategories(int page, int size, boolean orderAsc) {
        validatePageNumber(page);
        validatePageSize(size);
        return categoryPersistencePort.listCategories(page, size, orderAsc);
    }

    private void validateCategoryName(String name) {
        Objects.requireNonNull(name, DomainConstants.FIELD_NAME_NULL_MESSAGE);
        if (name.length() > DomainConstants.MAX_CATEGORY_NAME_LENGTH) {
            throw new CategoryNameMaxSizeExceededException();
        }
    }

    private void validateCategoryDescription(String description) {
        Objects.requireNonNull(description, DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
        if (description.length() > DomainConstants.MAX_CATEGORY_DESCRIPTION_LENGTH) {
            throw new CategoryDescriptionMaxSizeExceededException();
        }
    }

    private void checkIfCategoryAlreadyExists(String name) {
        CategoryModel existingCategory = categoryPersistencePort.getCategoryByName(name);
        if (existingCategory != null) {
            throw new CategoryAlreadyExistsException();
        }
    }

    private void validatePageNumber(int page) {
        if (page < DomainConstants.DEFAULT_PAGE_NUMBER) {
            throw new PageNumberNegativeException();
        }
    }

    private void validatePageSize(int size) {
        if (size <= DomainConstants.DEFAULT_SIZE_NUMBER) {
            throw new PageSizeInvalidException();
        }
    }
}