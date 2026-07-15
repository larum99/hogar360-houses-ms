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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryUseCaseTest {

    @Mock
    private CategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;

    private CategoryModel categoryModel;
    private String adminRole;
    private String userRole;

    @BeforeEach
    void setUp() {
        categoryModel = new CategoryModel();
        categoryModel.setName("Test Category");
        categoryModel.setDescription("Test Description");
        adminRole = DomainConstants.ROLE_ADMIN;
        userRole = "USER";
    }

    @Test
    void save_shouldSaveCategory_whenCategoryDoesNotExistAndRoleIsAdmin() {
        when(categoryPersistencePort.getCategoryByName(categoryModel.getName())).thenReturn(Optional.empty());

        categoryUseCase.save(categoryModel, adminRole);

        verify(categoryPersistencePort, times(1)).getCategoryByName(categoryModel.getName());
        verify(categoryPersistencePort, times(1)).save(categoryModel);
    }

    @Test
    void save_shouldThrowForbiddenException_whenRoleIsNotAdmin() {
        assertThrows(ForbiddenException.class, () -> categoryUseCase.save(categoryModel, userRole));

        verify(categoryPersistencePort, never()).getCategoryByName(any());
        verify(categoryPersistencePort, never()).save(any());
    }

    @Test
    void save_shouldThrowCategoryAlreadyExistsException_whenCategoryAlreadyExistsAndRoleIsAdmin() {
        when(categoryPersistencePort.getCategoryByName(categoryModel.getName()))
                .thenReturn(Optional.of(categoryModel));
        assertThrows(CategoryAlreadyExistsException.class, () -> categoryUseCase.save(categoryModel, adminRole));

        verify(categoryPersistencePort, times(1)).getCategoryByName(categoryModel.getName());
        verify(categoryPersistencePort, never()).save(any());
    }

    @Test
    void save_shouldThrowNullPointerException_whenNameIsNullAndRoleIsAdmin() {
        categoryModel.setName(null);
        Exception exception = assertThrows(NullPointerException.class, () -> categoryUseCase.save(categoryModel, adminRole));
        assertEquals(DomainConstants.FIELD_NAME_NULL_MESSAGE, exception.getMessage());
        verify(categoryPersistencePort, never()).getCategoryByName(any());
        verify(categoryPersistencePort, never()).save(any());
    }

    @Test
    void save_shouldThrowNullPointerException_whenDescriptionIsNullAndRoleIsAdmin() {
        categoryModel.setDescription(null);
        Exception exception = assertThrows(NullPointerException.class, () -> categoryUseCase.save(categoryModel, adminRole));
        assertEquals(DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE, exception.getMessage());
        verify(categoryPersistencePort, never()).getCategoryByName(any());
        verify(categoryPersistencePort, never()).save(any());
    }

    @Test
    void save_shouldThrowCategoryNameMaxSizeExceededException_whenNameExceedsMaxLengthAndRoleIsAdmin() {
        String longName = "a".repeat(DomainConstants.MAX_CATEGORY_NAME_LENGTH + 1);
        categoryModel.setName(longName);
        assertThrows(CategoryNameMaxSizeExceededException.class, () -> categoryUseCase.save(categoryModel, adminRole));
        verify(categoryPersistencePort, never()).getCategoryByName(any());
        verify(categoryPersistencePort, never()).save(any());
    }

    @Test
    void save_shouldThrowCategoryDescriptionMaxSizeExceededException_whenDescriptionExceedsMaxLengthAndRoleIsAdmin() {
        String longDescription = "a".repeat(DomainConstants.MAX_CATEGORY_DESCRIPTION_LENGTH + 1);
        categoryModel.setDescription(longDescription);
        assertThrows(CategoryDescriptionMaxSizeExceededException.class, () -> categoryUseCase.save(categoryModel, adminRole));
        verify(categoryPersistencePort, never()).getCategoryByName(any());
        verify(categoryPersistencePort, never()).save(any());
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
    void listCategories_shouldThrowPageNumberNegativeException_whenPageIsNegative() {
        int page = -1;
        int size = 10;
        boolean orderAsc = true;
        assertThrows(PageNumberNegativeException.class, () -> categoryUseCase.listCategories(page, size, orderAsc));
        verify(categoryPersistencePort, never()).listCategories(anyInt(), anyInt(), anyBoolean());
    }

    @Test
    void listCategories_shouldThrowPageSizeInvalidException_whenSizeIsInvalid() {
        int page = 0;
        int size = DomainConstants.DEFAULT_SIZE_NUMBER;
        boolean orderAsc = true;
        assertThrows(PageSizeInvalidException.class, () -> categoryUseCase.listCategories(page, size, orderAsc));
        verify(categoryPersistencePort, never()).listCategories(anyInt(), anyInt(), anyBoolean());
    }
}