package com.hogar360.houses.houses.domain.usecases;

import com.hogar360.houses.houses.domain.exceptions.*;
import com.hogar360.houses.houses.domain.model.CategoryModel;
import com.hogar360.houses.houses.domain.utils.PageResult;
import com.hogar360.houses.houses.domain.ports.out.CategoryPersistencePort;
import com.hogar360.houses.houses.domain.utils.constants.DomainConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
        categoryModel.setDescription("Test Description");
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
        PageResult<CategoryModel> expectedPage = new PageResult<>(categories, 2L, 1, 0, 10, true, true);

        when(categoryPersistencePort.listCategories(page, size, orderAsc)).thenReturn(expectedPage);

        PageResult<CategoryModel> actualPage = categoryUseCase.listCategories(page, size, orderAsc);

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
        PageResult<CategoryModel> expectedPage = new PageResult<>(categories, 2L, 1, 0, 10, true, true);

        when(categoryPersistencePort.listCategories(page, size, orderAsc)).thenReturn(expectedPage);

        PageResult<CategoryModel> actualPage = categoryUseCase.listCategories(page, size, orderAsc);

        assertEquals(expectedPage, actualPage);
        verify(categoryPersistencePort, times(1)).listCategories(page, size, orderAsc);
    }

    @Test
    void save_shouldThrowException_whenNameIsNull() {
        categoryModel.setName(null);
        assertThrows(NullPointerException.class, () -> categoryUseCase.save(categoryModel));
        verify(categoryPersistencePort, never()).save(any());
    }

    @Test
    void save_shouldThrowException_whenDescriptionIsNull() {
        categoryModel.setDescription(null);
        assertThrows(NullPointerException.class, () -> categoryUseCase.save(categoryModel));
        verify(categoryPersistencePort, never()).save(any());
    }

    @Test
    void save_shouldThrowException_whenNameExceedsMaxLength() {
        String longName = "a".repeat(DomainConstants.MAX_CATEGORY_NAME_LENGTH + 1);
        categoryModel.setName(longName);
        assertThrows(CategoryNameMaxSizeExceededException.class, () -> categoryUseCase.save(categoryModel));
        verify(categoryPersistencePort, never()).save(any());
    }

    @Test
    void save_shouldThrowException_whenDescriptionExceedsMaxLength() {
        String longDescription = "a".repeat(DomainConstants.MAX_CATEGORY_DESCRIPTION_LENGTH + 1);
        categoryModel.setDescription(longDescription);
        assertThrows(CategoryDescriptionMaxSizeExceededException.class, () -> categoryUseCase.save(categoryModel));
        verify(categoryPersistencePort, never()).save(any());
    }

    @Test
    void listCategories_shouldThrowException_whenPageIsNegative() {
        int page = -1;
        int size = 10;
        boolean orderAsc = true;
        assertThrows(PageNumberNegativeException.class, () -> categoryUseCase.listCategories(page, size, orderAsc));
        verify(categoryPersistencePort, never()).listCategories(anyInt(), anyInt(), anyBoolean());
    }

    @Test
    void listCategories_shouldThrowException_whenSizeIsInvalid() {
        int page = 0;
        int size = DomainConstants.DEFAULT_SIZE_NUMBER;
        boolean orderAsc = true;
        assertThrows(PageSizeInvalidException.class, () -> categoryUseCase.listCategories(page, size, orderAsc));
        verify(categoryPersistencePort, never()).listCategories(anyInt(), anyInt(), anyBoolean());
    }
}