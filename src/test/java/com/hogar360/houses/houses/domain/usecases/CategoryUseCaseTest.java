package com.hogar360.houses.houses.domain.usecases;

import com.hogar360.houses.houses.domain.exceptions.CategoryAlreadyExistsException;
import com.hogar360.houses.houses.domain.model.CategoryModel;
import com.hogar360.houses.houses.domain.model.PageModel;
import com.hogar360.houses.houses.domain.ports.out.CategoryPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryUseCaseTest {

    @Mock
    private CategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;

    private CategoryModel categoryModel;

    @BeforeEach
    void setUp() {
        categoryModel = new CategoryModel();
        categoryModel.setName("Test Category");
    }

    @Test
    void save_shouldSaveCategory_whenCategoryDoesNotExist() {
        when(categoryPersistencePort.getCategoryByName(categoryModel.getName())).thenReturn(null);

        categoryUseCase.save(categoryModel);

        verify(categoryPersistencePort, times(1)).save(categoryModel);
    }

    @Test
    void save_shouldThrowException_whenCategoryAlreadyExists() {
        when(categoryPersistencePort.getCategoryByName(categoryModel.getName())).thenReturn(categoryModel);

        assertThrows(CategoryAlreadyExistsException.class, () -> categoryUseCase.save(categoryModel));

        verify(categoryPersistencePort, never()).save(categoryModel);
    }

    @Test
    void listCategories_shouldReturnPage_whenCalledWithAscendingOrder() {
        int page = 0;
        int size = 10;
        boolean orderAsc = true;
        List<CategoryModel> categories = Arrays.asList(
                new CategoryModel(1L, "Category A", "Description A"),
                new CategoryModel(2L, "Category B", "Description B")
        );
        PageModel<CategoryModel> expectedPage = new PageModel<>(categories, 2, 1, 0, 10, true, true);

        when(categoryPersistencePort.listCategories(page, size, orderAsc)).thenReturn(expectedPage);

        PageModel<CategoryModel> actualPage = categoryUseCase.listCategories(page, size, orderAsc);

        assertEquals(expectedPage, actualPage);
        verify(categoryPersistencePort, times(1)).listCategories(page, size, orderAsc);
    }

    @Test
    void listCategories_shouldReturnPage_whenCalledWithDescendingOrder() {
        int page = 0;
        int size = 10;
        boolean orderAsc = false;
        List<CategoryModel> categories = Arrays.asList(
                new CategoryModel(2L, "Category B", "Description B"),
                new CategoryModel(1L, "Category A", "Description A")
        );
        PageModel<CategoryModel> expectedPage = new PageModel<>(categories, 2, 1, 0, 10, true, true);

        when(categoryPersistencePort.listCategories(page, size, orderAsc)).thenReturn(expectedPage);

        PageModel<CategoryModel> actualPage = categoryUseCase.listCategories(page, size, orderAsc);

        assertEquals(expectedPage, actualPage);
        verify(categoryPersistencePort, times(1)).listCategories(page, size, orderAsc);
    }
}