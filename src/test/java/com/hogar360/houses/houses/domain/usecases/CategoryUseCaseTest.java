package com.hogar360.houses.houses.domain.usecases;

import com.hogar360.houses.houses.domain.exceptions.*;
import com.hogar360.houses.houses.domain.model.CategoryModel;
import com.hogar360.houses.houses.domain.ports.in.RoleValidatorPort;
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

    @Mock
    private RoleValidatorPort roleValidatorPort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;

    private CategoryModel categoryModel;
    private String adminToken;
    private String userToken;

    @BeforeEach
    void setUp() {
        categoryModel = new CategoryModel();
        categoryModel.setName("Test Category");
        categoryModel.setDescription("Test Description");
        adminToken = "validAdminToken";
        userToken = "invalidUserToken";
    }

    @Test
    void save_shouldSaveCategory_whenCategoryDoesNotExistAndRoleIsAdmin() {
        when(roleValidatorPort.extractRole(adminToken)).thenReturn(DomainConstants.ROLE_ADMIN);
        when(categoryPersistencePort.getCategoryByName(categoryModel.getName())).thenReturn(null);

        categoryUseCase.save(categoryModel, adminToken);

        verify(roleValidatorPort, times(1)).extractRole(adminToken);
        verify(categoryPersistencePort, times(1)).getCategoryByName(categoryModel.getName());
        verify(categoryPersistencePort, times(1)).save(categoryModel);
    }

    @Test
    void save_shouldThrowForbiddenException_whenRoleIsNotAdmin() {
        when(roleValidatorPort.extractRole(userToken)).thenReturn("USER");

        assertThrows(ForbiddenException.class, () -> categoryUseCase.save(categoryModel, userToken));

        verify(roleValidatorPort, times(1)).extractRole(userToken);
        verify(categoryPersistencePort, never()).getCategoryByName(any());
        verify(categoryPersistencePort, never()).save(any());
    }

    @Test
    void save_shouldThrowCategoryAlreadyExistsException_whenCategoryAlreadyExistsAndRoleIsAdmin() {
        when(roleValidatorPort.extractRole(adminToken)).thenReturn(DomainConstants.ROLE_ADMIN);
        when(categoryPersistencePort.getCategoryByName(categoryModel.getName())).thenReturn(categoryModel);

        assertThrows(CategoryAlreadyExistsException.class, () -> categoryUseCase.save(categoryModel, adminToken));

        verify(roleValidatorPort, times(1)).extractRole(adminToken);
        verify(categoryPersistencePort, times(1)).getCategoryByName(categoryModel.getName());
        verify(categoryPersistencePort, never()).save(any());
    }

    @Test
    void save_shouldThrowNullPointerException_whenNameIsNullAndRoleIsAdmin() {
        when(roleValidatorPort.extractRole(adminToken)).thenReturn(DomainConstants.ROLE_ADMIN);
        categoryModel.setName(null);
        assertThrows(NullPointerException.class, () -> categoryUseCase.save(categoryModel, adminToken));
        verify(roleValidatorPort, times(1)).extractRole(adminToken);
        verify(categoryPersistencePort, never()).getCategoryByName(any());
        verify(categoryPersistencePort, never()).save(any());
    }

    @Test
    void save_shouldThrowNullPointerException_whenDescriptionIsNullAndRoleIsAdmin() {
        when(roleValidatorPort.extractRole(adminToken)).thenReturn(DomainConstants.ROLE_ADMIN);
        categoryModel.setDescription(null);
        assertThrows(NullPointerException.class, () -> categoryUseCase.save(categoryModel, adminToken));
        verify(roleValidatorPort, times(1)).extractRole(adminToken);
        verify(categoryPersistencePort, never()).getCategoryByName(any());
        verify(categoryPersistencePort, never()).save(any());
    }

    @Test
    void save_shouldThrowCategoryNameMaxSizeExceededException_whenNameExceedsMaxLengthAndRoleIsAdmin() {
        when(roleValidatorPort.extractRole(adminToken)).thenReturn(DomainConstants.ROLE_ADMIN);
        String longName = "a".repeat(DomainConstants.MAX_CATEGORY_NAME_LENGTH + 1);
        categoryModel.setName(longName);
        assertThrows(CategoryNameMaxSizeExceededException.class, () -> categoryUseCase.save(categoryModel, adminToken));
        verify(roleValidatorPort, times(1)).extractRole(adminToken);
        verify(categoryPersistencePort, never()).getCategoryByName(any());
        verify(categoryPersistencePort, never()).save(any());
    }

    @Test
    void save_shouldThrowCategoryDescriptionMaxSizeExceededException_whenDescriptionExceedsMaxLengthAndRoleIsAdmin() {
        when(roleValidatorPort.extractRole(adminToken)).thenReturn(DomainConstants.ROLE_ADMIN);
        String longDescription = "a".repeat(DomainConstants.MAX_CATEGORY_DESCRIPTION_LENGTH + 1);
        categoryModel.setDescription(longDescription);
        assertThrows(CategoryDescriptionMaxSizeExceededException.class, () -> categoryUseCase.save(categoryModel, adminToken));
        verify(roleValidatorPort, times(1)).extractRole(adminToken);
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