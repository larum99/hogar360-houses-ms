package com.hogar360.houses.houses.domain.usecases;

import com.hogar360.houses.houses.domain.criteria.HouseSearchCriteria;
import com.hogar360.houses.houses.domain.exceptions.*;
import com.hogar360.houses.houses.domain.model.*;
import com.hogar360.houses.houses.domain.ports.out.HousePersistencePort;
import com.hogar360.houses.houses.domain.ports.out.CategoryPersistencePort;
import com.hogar360.houses.houses.domain.ports.out.LocationPersistencePort;
import com.hogar360.houses.houses.domain.ports.in.RoleValidatorPort; // Import RoleValidatorPort
import com.hogar360.houses.houses.domain.utils.PublicationStatus;
import com.hogar360.houses.houses.domain.utils.constants.DomainConstants;
import com.hogar360.houses.houses.domain.utils.PageResult;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HouseUseCaseTest {

    @Mock
    private HousePersistencePort housePersistencePort;

    @Mock
    private CategoryPersistencePort categoryPersistencePort;

    @Mock
    private LocationPersistencePort locationPersistencePort;

    @Mock
    private RoleValidatorPort roleValidatorPort; // Mock the RoleValidatorPort

    @InjectMocks
    private HouseUseCase houseUseCase;

    private HouseModel validHouseModel;
    private CategoryModel existingCategory;
    private LocationModel existingLocation;
    private DepartmentModel department;
    private CityModel city;
    private String sellerToken;
    private String otherToken;

    @BeforeEach
    void setUp() {
        existingCategory = new CategoryModel(1L, "Apartment", null);
        department = new DepartmentModel(1L, "Nombre del Departamento", null);
        city = new CityModel(1L, "Nombre de la Ciudad", null, department);
        existingLocation = new LocationModel(1L, city, "Sector A");
        validHouseModel = new HouseModel(
                null,
                "Beautiful Apartment",
                "Description of the apartment",
                existingCategory,
                3,
                2,
                new BigDecimal("150000.00"),
                existingLocation,
                LocalDate.now(),
                LocalDate.now().plusDays(5),
                PublicationStatus.PAUSED
        );
        sellerToken = "validSellerToken";
        otherToken = "invalidToken";
    }

    @Test
    void save_validHouseModel_savesSuccessfully_whenRoleIsSeller() {
        when(roleValidatorPort.extractRole(sellerToken)).thenReturn(DomainConstants.ROLE_SELLER);
        when(categoryPersistencePort.findById(existingCategory.getId())).thenReturn(Optional.of(existingCategory));
        when(locationPersistencePort.findById(existingLocation.getId())).thenReturn(Optional.of(existingLocation));

        houseUseCase.save(validHouseModel, sellerToken); // Pass the token

        ArgumentCaptor<HouseModel> houseModelCaptor = ArgumentCaptor.forClass(HouseModel.class);
        verify(roleValidatorPort, times(1)).extractRole(sellerToken);
        verify(housePersistencePort, times(1)).save(houseModelCaptor.capture());
        HouseModel savedHouse = houseModelCaptor.getValue();

        assertEquals("Beautiful Apartment", savedHouse.getName());
        assertEquals(PublicationStatus.PAUSED, savedHouse.getStatus());
        assertEquals(LocalDate.now(), savedHouse.getPublicationDate());
    }

    @Test
    void save_shouldThrowForbiddenException_whenRoleIsNotSeller() {
        when(roleValidatorPort.extractRole(otherToken)).thenReturn("BUYER");

        assertThrows(ForbiddenException.class, () -> houseUseCase.save(validHouseModel, otherToken)); // Pass the token

        verify(roleValidatorPort, times(1)).extractRole(otherToken);
        verifyNoInteractions(housePersistencePort);
        verifyNoInteractions(categoryPersistencePort);
        verifyNoInteractions(locationPersistencePort);
    }


    @Test
    void save_validHouseModel_futurePublicationDate_savesWithPausedStatus_whenRoleIsSeller() {
        when(roleValidatorPort.extractRole(sellerToken)).thenReturn(DomainConstants.ROLE_SELLER);
        LocalDate futureDate = LocalDate.now().plusDays(10);
        validHouseModel.setActivePublicationDate(futureDate);
        when(categoryPersistencePort.findById(existingCategory.getId())).thenReturn(Optional.of(existingCategory));
        when(locationPersistencePort.findById(existingLocation.getId())).thenReturn(Optional.of(existingLocation));

        houseUseCase.save(validHouseModel, sellerToken); // Pass the token

        ArgumentCaptor<HouseModel> houseModelCaptor = ArgumentCaptor.forClass(HouseModel.class);
        verify(roleValidatorPort, times(1)).extractRole(sellerToken);
        verify(housePersistencePort, times(1)).save(houseModelCaptor.capture());
        assertEquals(PublicationStatus.PAUSED, houseModelCaptor.getValue().getStatus());
    }

    @Test
    void save_nullName_throwsRequiredFieldException_whenRoleIsSeller() {
        when(roleValidatorPort.extractRole(sellerToken)).thenReturn(DomainConstants.ROLE_SELLER);
        HouseModel houseModelWithoutName = new HouseModel(null, null, "Desc", existingCategory, 3, 2, new BigDecimal("100"), existingLocation, LocalDate.now(), LocalDate.now(), PublicationStatus.PUBLISHED);
        assertThrows(NullPointerException.class, () -> houseUseCase.save(houseModelWithoutName, sellerToken), DomainConstants.FIELD_NAME_NULL_MESSAGE); // Pass the token
        verify(roleValidatorPort, times(1)).extractRole(sellerToken);
        verifyNoInteractions(housePersistencePort);
        verifyNoInteractions(categoryPersistencePort);
        verifyNoInteractions(locationPersistencePort);
    }

    @Test
    void save_nullDescription_throwsRequiredFieldException_whenRoleIsSeller() {
        when(roleValidatorPort.extractRole(sellerToken)).thenReturn(DomainConstants.ROLE_SELLER);
        HouseModel houseModelWithoutDescription = new HouseModel(null, "Name", null, existingCategory, 3, 2, new BigDecimal("100"), existingLocation, LocalDate.now(), LocalDate.now(), PublicationStatus.PUBLISHED);
        assertThrows(NullPointerException.class, () -> houseUseCase.save(houseModelWithoutDescription, sellerToken), DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE); // Pass the token
        verify(roleValidatorPort, times(1)).extractRole(sellerToken);
        verifyNoInteractions(housePersistencePort);
        verifyNoInteractions(categoryPersistencePort);
        verifyNoInteractions(locationPersistencePort);
    }

    @Test
    void save_nullCategory_throwsRequiredFieldException_whenRoleIsSeller() {
        when(roleValidatorPort.extractRole(sellerToken)).thenReturn(DomainConstants.ROLE_SELLER);
        HouseModel houseModelWithoutCategory = new HouseModel(null, "Name", "Desc", null, 3, 2, new BigDecimal("100"), existingLocation, LocalDate.now(), LocalDate.now(), PublicationStatus.PUBLISHED);
        assertThrows(NullPointerException.class, () -> houseUseCase.save(houseModelWithoutCategory, sellerToken), DomainConstants.FIELD_CATEGORY_NULL_MESSAGE); // Pass the token
        verify(roleValidatorPort, times(1)).extractRole(sellerToken);
        verifyNoInteractions(housePersistencePort);
        verifyNoInteractions(categoryPersistencePort);
        verifyNoInteractions(locationPersistencePort);
    }

    @Test
    void save_nullPrice_throwsRequiredFieldException_whenRoleIsSeller() {
        when(roleValidatorPort.extractRole(sellerToken)).thenReturn(DomainConstants.ROLE_SELLER);
        HouseModel houseModelWithoutPrice = new HouseModel(null, "Name", "Desc", existingCategory, 3, 2, null, existingLocation, LocalDate.now(), LocalDate.now(), PublicationStatus.PUBLISHED);
        assertThrows(NullPointerException.class, () -> houseUseCase.save(houseModelWithoutPrice, sellerToken), DomainConstants.FIELD_PRICE_NULL_MESSAGE); // Pass the token
        verify(roleValidatorPort, times(1)).extractRole(sellerToken);
        verifyNoInteractions(housePersistencePort);
        verifyNoInteractions(categoryPersistencePort);
        verifyNoInteractions(locationPersistencePort);
    }

    @Test
    void save_nullLocation_throwsRequiredFieldException_whenRoleIsSeller() {
        when(roleValidatorPort.extractRole(sellerToken)).thenReturn(DomainConstants.ROLE_SELLER);
        HouseModel houseModelWithoutLocation = new HouseModel(null, "Name", "Desc", existingCategory, 3, 2, new BigDecimal("100"), null, LocalDate.now(), LocalDate.now(), PublicationStatus.PUBLISHED);
        assertThrows(NullPointerException.class, () -> houseUseCase.save(houseModelWithoutLocation, sellerToken), DomainConstants.FIELD_LOCATION_NULL_MESSAGE); // Pass the token
        verify(roleValidatorPort, times(1)).extractRole(sellerToken);
        verifyNoInteractions(housePersistencePort);
        verifyNoInteractions(categoryPersistencePort);
        verifyNoInteractions(locationPersistencePort);
    }

    @Test
    void save_nullActivePublicationDate_throwsRequiredFieldException_whenRoleIsSeller() {
        when(roleValidatorPort.extractRole(sellerToken)).thenReturn(DomainConstants.ROLE_SELLER);
        HouseModel houseModelWithoutActivePublicationDate = new HouseModel(null, "Name", "Desc", existingCategory, 3, 2, new BigDecimal("100"), existingLocation, LocalDate.now(), null, PublicationStatus.PUBLISHED);
        assertThrows(NullPointerException.class, () -> houseUseCase.save(houseModelWithoutActivePublicationDate, sellerToken), DomainConstants.FIELD_ACTIVE_PUBLICATION_DATE_NULL_MESSAGE); // Pass the token
        verify(roleValidatorPort, times(1)).extractRole(sellerToken);
        verifyNoInteractions(housePersistencePort);
        verifyNoInteractions(categoryPersistencePort);
        verifyNoInteractions(locationPersistencePort);
    }

    @Test
    void save_minimumBedroomsNotMet_throwsMinimumBedroomsException_whenRoleIsSeller() {
        when(roleValidatorPort.extractRole(sellerToken)).thenReturn(DomainConstants.ROLE_SELLER);
        validHouseModel.setBedrooms(DomainConstants.MIN_BEDROOMS - 1);
        when(categoryPersistencePort.findById(existingCategory.getId())).thenReturn(Optional.of(existingCategory));
        when(locationPersistencePort.findById(existingLocation.getId())).thenReturn(Optional.of(existingLocation));
        assertThrows(HouseMinimumBedroomsRequiredException.class, () -> houseUseCase.save(validHouseModel, sellerToken)); // Pass the token
        verify(roleValidatorPort, times(1)).extractRole(sellerToken);
        verifyNoInteractions(housePersistencePort);
    }

    @Test
    void save_minimumBathroomsNotMet_throwsMinimumBathroomsException_whenRoleIsSeller() {
        when(roleValidatorPort.extractRole(sellerToken)).thenReturn(DomainConstants.ROLE_SELLER);
        validHouseModel.setBathrooms(DomainConstants.MIN_BATHROOMS - 1);
        when(categoryPersistencePort.findById(existingCategory.getId())).thenReturn(Optional.of(existingCategory));
        when(locationPersistencePort.findById(existingLocation.getId())).thenReturn(Optional.of(existingLocation));
        assertThrows(HouseMinimumBathroomsRequiredException.class, () -> houseUseCase.save(validHouseModel, sellerToken)); // Pass the token
        verify(roleValidatorPort, times(1)).extractRole(sellerToken);
        verifyNoInteractions(housePersistencePort);
    }

    @Test
    void save_minimumPriceNotMet_throwsMinimumPriceException_whenRoleIsSeller() {
        when(roleValidatorPort.extractRole(sellerToken)).thenReturn(DomainConstants.ROLE_SELLER);
        validHouseModel.setPrice(DomainConstants.MIN_PRICE);
        when(categoryPersistencePort.findById(existingCategory.getId())).thenReturn(Optional.of(existingCategory));
        when(locationPersistencePort.findById(existingLocation.getId())).thenReturn(Optional.of(existingLocation));
        assertThrows(HouseMinimumRequirePriceException.class, () -> houseUseCase.save(validHouseModel, sellerToken)); // Pass the token
        validHouseModel.setPrice(DomainConstants.MIN_PRICE.subtract(BigDecimal.ONE));
        assertThrows(HouseMinimumRequirePriceException.class, () -> houseUseCase.save(validHouseModel, sellerToken)); // Pass the token
        verify(roleValidatorPort, times(2)).extractRole(sellerToken); // Called twice due to two assertions
        verifyNoInteractions(housePersistencePort);
    }

    @Test
    void save_invalidPublicationDateInPast_throwsInvalidPublicationDateException_whenRoleIsSeller() {
        when(roleValidatorPort.extractRole(sellerToken)).thenReturn(DomainConstants.ROLE_SELLER);
        validHouseModel.setActivePublicationDate(LocalDate.now().minusDays(1));
        when(categoryPersistencePort.findById(existingCategory.getId())).thenReturn(Optional.of(existingCategory));
        when(locationPersistencePort.findById(existingLocation.getId())).thenReturn(Optional.of(existingLocation));
        assertThrows(InvalidPublicationDateException.class, () -> houseUseCase.save(validHouseModel, sellerToken)); // Pass the token
        verify(roleValidatorPort, times(1)).extractRole(sellerToken);
        verifyNoInteractions(housePersistencePort);
    }

    @Test
    void save_invalidPublicationDateTooFarInFuture_throwsInvalidPublicationDateException_whenRoleIsSeller() {
        when(roleValidatorPort.extractRole(sellerToken)).thenReturn(DomainConstants.ROLE_SELLER);
        validHouseModel.setActivePublicationDate(LocalDate.now().plusDays(DomainConstants.MAX_PUBLICATION_DAYS + 1));
        when(categoryPersistencePort.findById(existingCategory.getId())).thenReturn(Optional.of(existingCategory));
        when(locationPersistencePort.findById(existingLocation.getId())).thenReturn(Optional.of(existingLocation));
        assertThrows(InvalidPublicationDateException.class, () -> houseUseCase.save(validHouseModel, sellerToken)); // Pass the token
        verify(roleValidatorPort, times(1)).extractRole(sellerToken);
        verifyNoInteractions(housePersistencePort);
    }

    @Test
    void save_categoryNotFound_throwsCategoryNotFoundException_whenRoleIsSeller() {
        when(roleValidatorPort.extractRole(sellerToken)).thenReturn(DomainConstants.ROLE_SELLER);
        when(categoryPersistencePort.findById(existingCategory.getId())).thenReturn(Optional.empty());
        assertThrows(HouseCategoryNotFoundException.class, () -> houseUseCase.save(validHouseModel, sellerToken)); // Pass the token
        verify(roleValidatorPort, times(1)).extractRole(sellerToken);
        verifyNoInteractions(housePersistencePort);
    }

    @Test
    void save_locationNotFound_throwsLocationNotFoundException_whenRoleIsSeller() {
        when(roleValidatorPort.extractRole(sellerToken)).thenReturn(DomainConstants.ROLE_SELLER);
        when(categoryPersistencePort.findById(existingCategory.getId())).thenReturn(Optional.of(existingCategory));
        when(locationPersistencePort.findById(existingLocation.getId())).thenReturn(Optional.empty());
        assertThrows(HouseLocationNotFoundException.class, () -> houseUseCase.save(validHouseModel, sellerToken)); // Pass the token
        verify(roleValidatorPort, times(1)).extractRole(sellerToken);
        verifyNoInteractions(housePersistencePort);
    }

    @Test
    void save_validHouseModel_activePublicationDateIsNow_savesWithPublishedStatus_whenRoleIsSeller() {
        when(roleValidatorPort.extractRole(sellerToken)).thenReturn(DomainConstants.ROLE_SELLER);
        validHouseModel.setActivePublicationDate(LocalDate.now());

        when(categoryPersistencePort.findById(existingCategory.getId())).thenReturn(Optional.of(existingCategory));
        when(locationPersistencePort.findById(existingLocation.getId())).thenReturn(Optional.of(existingLocation));

        houseUseCase.save(validHouseModel, sellerToken); // Pass the token

        ArgumentCaptor<HouseModel> houseModelCaptor = ArgumentCaptor.forClass(HouseModel.class);
        verify(roleValidatorPort, times(1)).extractRole(sellerToken);
        verify(housePersistencePort, times(1)).save(houseModelCaptor.capture());
        HouseModel savedHouse = houseModelCaptor.getValue();

        assertEquals("Beautiful Apartment", savedHouse.getName());
        assertEquals(PublicationStatus.PUBLISHED, savedHouse.getStatus());
        assertEquals(LocalDate.now(), savedHouse.getPublicationDate());
    }

    @Test
    void searchHouses_negativePageNumber_throwsPageNumberNegativeException() {
        HouseSearchCriteria criteria = new HouseSearchCriteria();
        criteria.setPage(DomainConstants.DEFAULT_PAGE_NUMBER - 1);
        assertThrows(PageNumberNegativeException.class, () -> houseUseCase.searchHouses(criteria));
        verifyNoInteractions(housePersistencePort);
    }

    @Test
    void searchHouses_invalidPageSize_throwsPageSizeInvalidException() {
        HouseSearchCriteria criteria = new HouseSearchCriteria();
        criteria.setSize(DomainConstants.DEFAULT_SIZE_NUMBER);
        assertThrows(PageSizeInvalidException.class, () -> houseUseCase.searchHouses(criteria));
        criteria.setSize(DomainConstants.DEFAULT_SIZE_NUMBER - 1);
        assertThrows(PageSizeInvalidException.class, () -> houseUseCase.searchHouses(criteria));
        verifyNoInteractions(housePersistencePort);
    }

    @Test
    void searchHouses_minimumBedroomsNotMet_throwsMinimumBedroomsException() {
        HouseSearchCriteria criteria = new HouseSearchCriteria();
        criteria.setSize(DomainConstants.DEFAULT_SIZE_NUMBER + 1);
        criteria.setBedrooms(DomainConstants.MIN_BEDROOMS - 1);
        assertThrows(HouseMinimumBedroomsRequiredException.class, () -> houseUseCase.searchHouses(criteria));
        verifyNoInteractions(housePersistencePort);
    }

    @Test
    void searchHouses_minimumBathroomsNotMet_throwsMinimumBathroomsException() {
        HouseSearchCriteria criteria = new HouseSearchCriteria();
        criteria.setSize(DomainConstants.DEFAULT_SIZE_NUMBER + 1);
        criteria.setBathrooms(DomainConstants.MIN_BATHROOMS - 1);
        assertThrows(HouseMinimumBathroomsRequiredException.class, () -> houseUseCase.searchHouses(criteria));
        verifyNoInteractions(housePersistencePort);
    }

    @Test
    void searchHouses_minimumPriceNotMet_throwsMinimumPriceException() {
        HouseSearchCriteria criteria = new HouseSearchCriteria();
        criteria.setSize(DomainConstants.DEFAULT_SIZE_NUMBER + 1);
        criteria.setMinPrice(DomainConstants.MIN_PRICE);
        assertThrows(HouseMinimumRequirePriceException.class, () -> houseUseCase.searchHouses(criteria));
        criteria.setMinPrice(DomainConstants.MIN_PRICE.subtract(BigDecimal.ONE));
        assertThrows(HouseMinimumRequirePriceException.class, () -> houseUseCase.searchHouses(criteria));
        verifyNoInteractions(housePersistencePort);
    }

    @Test
    void searchHouses_invalidSortDirection_throwsInvalidSortDirectionException() {
        HouseSearchCriteria criteria = new HouseSearchCriteria();
        criteria.setSize(DomainConstants.DEFAULT_SIZE_NUMBER + 1);
        criteria.setSortDirection("invalid");
        assertThrows(InvalidSortDirectionException.class, () -> houseUseCase.searchHouses(criteria));
        criteria.setSortDirection("ascc");
        assertThrows(InvalidSortDirectionException.class, () -> houseUseCase.searchHouses(criteria));
        verifyNoInteractions(housePersistencePort);
    }

    @Test
    void searchHouses_invalidSortByField_throwsInvalidSortByFieldException() {
        HouseSearchCriteria criteria = new HouseSearchCriteria();
        criteria.setSize(DomainConstants.DEFAULT_SIZE_NUMBER + 1);
        criteria.setSortBy("invalidField");
        assertThrows(InvalidSortByFieldException.class, () -> houseUseCase.searchHouses(criteria));
        // This case is now valid according to the updated validateSortBy
        // criteria.setSortBy("name");
        // assertThrows(InvalidSortByFieldException.class, () -> houseUseCase.searchHouses(criteria));
        verifyNoInteractions(housePersistencePort);
    }

    @Test
    void searchHouses_validSortByFieldPrice_callsPersistencePort() {
        HouseSearchCriteria criteria = new HouseSearchCriteria();
        criteria.setPage(1);
        criteria.setSize(10);
        criteria.setSortBy(DomainConstants.SORT_BY_PRICE);
        when(housePersistencePort.search(ArgumentMatchers.any(HouseSearchCriteria.class))).thenReturn(new PageResult<>(Collections.emptyList(), 0L, 1, 10, 1, true, true));
        houseUseCase.searchHouses(criteria);
        verify(housePersistencePort, times(1)).search(criteria);
    }

    @Test
    void searchHouses_validSortByFieldBedrooms_callsPersistencePort() {
        HouseSearchCriteria criteria = new HouseSearchCriteria();
        criteria.setPage(1);
        criteria.setSize(10);
        criteria.setSortBy(DomainConstants.SORT_BY_BEDROOMS);
        when(housePersistencePort.search(ArgumentMatchers.any(HouseSearchCriteria.class))).thenReturn(new PageResult<>(Collections.emptyList(), 0L, 1, 10, 1, true, true));
        houseUseCase.searchHouses(criteria);
        verify(housePersistencePort, times(1)).search(criteria);
    }

    @Test
    void searchHouses_validSortByFieldBathrooms_callsPersistencePort() {
        HouseSearchCriteria criteria = new HouseSearchCriteria();
        criteria.setPage(1);
        criteria.setSize(10);
        criteria.setSortBy(DomainConstants.SORT_BY_BATHROOMS);
        when(housePersistencePort.search(ArgumentMatchers.any(HouseSearchCriteria.class))).thenReturn(new PageResult<>(Collections.emptyList(), 0L, 1, 10, 1, true, true));
        houseUseCase.searchHouses(criteria);
        verify(housePersistencePort, times(1)).search(criteria);
    }

    @Test
    void searchHouses_validSortByFieldPublicationDate_callsPersistencePort() {
        HouseSearchCriteria criteria = new HouseSearchCriteria();
        criteria.setPage(1);
        criteria.setSize(10);
        criteria.setSortBy(DomainConstants.SORT_BY_PUBLICATION_DATE);
        when(housePersistencePort.search(ArgumentMatchers.any(HouseSearchCriteria.class))).thenReturn(new PageResult<>(Collections.emptyList(), 0L, 1, 10, 1, true, true));
        houseUseCase.searchHouses(criteria);
        verify(housePersistencePort, times(1)).search(criteria);
    }

    @Test
    void searchHouses_validSortByFieldCategory_callsPersistencePort() {
        HouseSearchCriteria criteria = new HouseSearchCriteria();
        criteria.setPage(1);
        criteria.setSize(10);
        criteria.setSortBy(DomainConstants.SORT_BY_CATEGORY);
        when(housePersistencePort.search(ArgumentMatchers.any(HouseSearchCriteria.class))).thenReturn(new PageResult<>(Collections.emptyList(), 0L, 1, 10, 1, true, true));
        houseUseCase.searchHouses(criteria);
        verify(housePersistencePort, times(1)).search(criteria);
    }


    @Test
    void searchHouses_validCriteria_callsPersistencePort() {
        HouseSearchCriteria criteria = new HouseSearchCriteria();
        criteria.setPage(1);
        criteria.setSize(10);
        when(housePersistencePort.search(ArgumentMatchers.any(HouseSearchCriteria.class))).thenReturn(new PageResult<>(Collections.emptyList(), 0L, 1, 10, 1, true, true));
        houseUseCase.searchHouses(criteria);
        verify(housePersistencePort, times(1)).search(criteria);
    }
}