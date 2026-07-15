package com.hogar360.houses.houses.domain.usecases;

import com.hogar360.houses.houses.domain.criteria.HouseSearchCriteria;
import com.hogar360.houses.houses.domain.exceptions.*;
import com.hogar360.houses.houses.domain.model.*;
import com.hogar360.houses.houses.domain.ports.out.HousePersistencePort;
import com.hogar360.houses.houses.domain.ports.out.CategoryPersistencePort;
import com.hogar360.houses.houses.domain.ports.out.LocationPersistencePort;
import com.hogar360.houses.houses.domain.utils.PublicationStatus;
import com.hogar360.houses.houses.domain.utils.constants.DomainConstants;
import com.hogar360.houses.houses.domain.utils.PageResult;
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
import java.util.Collections;
import java.util.List;
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

    @InjectMocks
    private HouseUseCase houseUseCase;

    private HouseModel validHouseModel;
    private CategoryModel existingCategory;
    private LocationModel existingLocation;
    private DepartmentModel department;
    private CityModel city;
    private String sellerRole;
    private String adminRole;
    private String anonymousRole;
    private String buyerRole;
    private Long sellerId;
    private Long otherUserId;

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
                PublicationStatus.PAUSED,
                sellerId
        );
        sellerRole = DomainConstants.ROLE_SELLER;
        adminRole = DomainConstants.ROLE_ADMIN;
        anonymousRole = DomainConstants.ROLE_ANONYMOUS;
        buyerRole = "BUYER";
        sellerId = 101L;
        otherUserId = 202L;
    }

    @Test
    void save_validHouseModel_savesSuccessfully_whenRoleIsSeller() {
        when(categoryPersistencePort.findById(existingCategory.getId())).thenReturn(Optional.of(existingCategory));
        when(locationPersistencePort.findById(existingLocation.getId())).thenReturn(Optional.of(existingLocation));
        when(housePersistencePort.existsByNameAndLocationId(validHouseModel.getName(), existingLocation.getId())).thenReturn(false);

        houseUseCase.save(validHouseModel, sellerRole, sellerId);

        ArgumentCaptor<HouseModel> houseModelCaptor = ArgumentCaptor.forClass(HouseModel.class);
        verify(housePersistencePort, times(1)).save(houseModelCaptor.capture());
        HouseModel savedHouse = houseModelCaptor.getValue();

        assertEquals("Beautiful Apartment", savedHouse.getName());
        assertEquals(PublicationStatus.PAUSED, savedHouse.getStatus());
        assertEquals(LocalDate.now(), savedHouse.getPublicationDate());
        assertEquals(sellerId, savedHouse.getPublisherId());
    }

    @Test
    void save_shouldThrowForbiddenException_whenRoleIsNotSeller() {
        assertThrows(ForbiddenException.class, () -> houseUseCase.save(validHouseModel, buyerRole, otherUserId));
        verifyNoInteractions(housePersistencePort);
        verifyNoInteractions(categoryPersistencePort);
        verifyNoInteractions(locationPersistencePort);
    }

    @Test
    void save_validHouseModel_futurePublicationDate_savesWithPausedStatus_whenRoleIsSeller() {
        LocalDate futureDate = LocalDate.now().plusDays(10);
        validHouseModel.setActivePublicationDate(futureDate);
        when(categoryPersistencePort.findById(existingCategory.getId())).thenReturn(Optional.of(existingCategory));
        when(locationPersistencePort.findById(existingLocation.getId())).thenReturn(Optional.of(existingLocation));
        when(housePersistencePort.existsByNameAndLocationId(validHouseModel.getName(), existingLocation.getId())).thenReturn(false);

        houseUseCase.save(validHouseModel, sellerRole, sellerId);

        ArgumentCaptor<HouseModel> houseModelCaptor = ArgumentCaptor.forClass(HouseModel.class);
        verify(housePersistencePort, times(1)).save(houseModelCaptor.capture());
        assertEquals(PublicationStatus.PAUSED, houseModelCaptor.getValue().getStatus());
    }

    @Test
    void save_nullName_throwsRequiredFieldException_whenRoleIsSeller() {
        HouseModel houseModelWithoutName = new HouseModel(null, null, "Desc", existingCategory, 3, 2, new BigDecimal("100"), existingLocation, LocalDate.now(), LocalDate.now(), PublicationStatus.PUBLISHED, sellerId);
        NullPointerException thrown = assertThrows(NullPointerException.class, () -> houseUseCase.save(houseModelWithoutName, sellerRole, sellerId));
        assertEquals(DomainConstants.FIELD_NAME_NULL_MESSAGE, thrown.getMessage());
        verifyNoInteractions(housePersistencePort);
        verifyNoInteractions(categoryPersistencePort);
        verifyNoInteractions(locationPersistencePort);
    }

    @Test
    void save_nullDescription_throwsRequiredFieldException_whenRoleIsSeller() {
        HouseModel houseModelWithoutDescription = new HouseModel(
                null,
                "Name",
                null,
                existingCategory,
                3,
                2,
                new BigDecimal("100"),
                existingLocation,
                LocalDate.now(),
                LocalDate.now(),
                PublicationStatus.PUBLISHED,
                sellerId);
        NullPointerException thrown = assertThrows(NullPointerException.class, () -> houseUseCase.save(houseModelWithoutDescription, sellerRole, sellerId));
        assertEquals(DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE, thrown.getMessage());
        verifyNoInteractions(housePersistencePort);
        verifyNoInteractions(categoryPersistencePort);
        verifyNoInteractions(locationPersistencePort);
    }

    @Test
    void save_nullCategory_throwsRequiredFieldException_whenRoleIsSeller() {
        HouseModel houseModelWithoutCategory = new HouseModel(
                null,
                "Name",
                "Valid Description",
                null,
                3,
                2,
                new BigDecimal("100"),
                existingLocation,
                LocalDate.now(),
                LocalDate.now(),
                PublicationStatus.PUBLISHED,
                sellerId);
        NullPointerException thrown = assertThrows(NullPointerException.class, () -> houseUseCase.save(houseModelWithoutCategory, sellerRole, sellerId));
        assertEquals(DomainConstants.FIELD_CATEGORY_NULL_MESSAGE, thrown.getMessage());
        verifyNoInteractions(housePersistencePort);
        verifyNoInteractions(categoryPersistencePort);
        verifyNoInteractions(locationPersistencePort);
    }

    @Test
    void save_nullPrice_throwsRequiredFieldException_whenRoleIsSeller() {
        HouseModel houseModelWithoutPrice = new HouseModel(
                null,
                "Name",
                "Valid Description",
                existingCategory,
                3,
                2,
                null,
                existingLocation,
                LocalDate.now(),
                LocalDate.now(),
                PublicationStatus.PUBLISHED,
                sellerId);
        NullPointerException thrown = assertThrows(NullPointerException.class, () -> houseUseCase.save(houseModelWithoutPrice, sellerRole, sellerId));
        assertEquals(DomainConstants.FIELD_PRICE_NULL_MESSAGE, thrown.getMessage());
        verifyNoInteractions(housePersistencePort);
        verifyNoInteractions(categoryPersistencePort);
        verifyNoInteractions(locationPersistencePort);
    }

    @Test
    void save_nullLocation_throwsRequiredFieldException_whenRoleIsSeller() {
        HouseModel houseModelWithoutLocation = new HouseModel(
                null,
                "Name",
                "Valid Description",
                existingCategory,
                3,
                2,
                new BigDecimal("100"),
                null,
                LocalDate.now(),
                LocalDate.now(),
                PublicationStatus.PUBLISHED,
                sellerId);
        NullPointerException thrown = assertThrows(NullPointerException.class, () -> houseUseCase.save(houseModelWithoutLocation, sellerRole, sellerId));
        assertEquals(DomainConstants.FIELD_LOCATION_NULL_MESSAGE, thrown.getMessage());
        verifyNoInteractions(housePersistencePort);
        verifyNoInteractions(categoryPersistencePort);
        verifyNoInteractions(locationPersistencePort);
    }

    @Test
    void save_nullActivePublicationDate_throwsRequiredFieldException_whenRoleIsSeller() {
        HouseModel houseModelWithoutActivePublicationDate = new HouseModel(
                null,
                "Name",
                "Valid Description",
                existingCategory,
                3,
                2,
                new BigDecimal("100"),
                existingLocation,
                LocalDate.now(),
                null,
                PublicationStatus.PUBLISHED,
                sellerId);

        NullPointerException thrown = assertThrows(NullPointerException.class, () -> houseUseCase.save(houseModelWithoutActivePublicationDate, sellerRole, sellerId));
        assertEquals(DomainConstants.FIELD_ACTIVE_PUBLICATION_DATE_NULL_MESSAGE, thrown.getMessage());

        verifyNoInteractions(housePersistencePort);
    }


    @Test
    void save_minimumBedroomsNotMet_throwsMinimumBedroomsException_whenRoleIsSeller() {
        validHouseModel.setBedrooms(DomainConstants.MIN_BEDROOMS - 1);
        when(categoryPersistencePort.findById(existingCategory.getId())).thenReturn(Optional.of(existingCategory));
        when(locationPersistencePort.findById(existingLocation.getId())).thenReturn(Optional.of(existingLocation));
        assertThrows(HouseMinimumBedroomsRequiredException.class, () -> houseUseCase.save(validHouseModel, sellerRole, sellerId));
        verify(housePersistencePort, never()).save(any(HouseModel.class));
    }

    @Test
    void save_minimumBathroomsNotMet_throwsMinimumBathroomsException_whenRoleIsSeller() {
        validHouseModel.setBathrooms(DomainConstants.MIN_BATHROOMS - 1);
        when(categoryPersistencePort.findById(existingCategory.getId())).thenReturn(Optional.of(existingCategory));
        when(locationPersistencePort.findById(existingLocation.getId())).thenReturn(Optional.of(existingLocation));
        assertThrows(HouseMinimumBathroomsRequiredException.class, () -> houseUseCase.save(validHouseModel, sellerRole, sellerId));
        verify(housePersistencePort, never()).save(any(HouseModel.class));
    }

    @Test
    void save_minimumPriceNotMet_throwsMinimumPriceException_whenRoleIsSeller() {
        validHouseModel.setPrice(DomainConstants.MIN_PRICE);
        when(categoryPersistencePort.findById(existingCategory.getId())).thenReturn(Optional.of(existingCategory));
        when(locationPersistencePort.findById(existingLocation.getId())).thenReturn(Optional.of(existingLocation));
        assertThrows(HouseMinimumRequirePriceException.class, () -> houseUseCase.save(validHouseModel, sellerRole, sellerId));
        verify(housePersistencePort, never()).save(any(HouseModel.class));
    }

    @Test
    void save_minimumPriceLessThanMin_throwsMinimumPriceException_whenRoleIsSeller() {
        validHouseModel.setPrice(DomainConstants.MIN_PRICE.subtract(BigDecimal.ONE));
        when(categoryPersistencePort.findById(existingCategory.getId())).thenReturn(Optional.of(existingCategory));
        when(locationPersistencePort.findById(existingLocation.getId())).thenReturn(Optional.of(existingLocation));
        assertThrows(HouseMinimumRequirePriceException.class, () -> houseUseCase.save(validHouseModel, sellerRole, sellerId));
        verify(housePersistencePort, never()).save(any(HouseModel.class));
    }

    @Test
    void save_invalidPublicationDateInPast_throwsInvalidPublicationDateException_whenRoleIsSeller() {
        validHouseModel.setActivePublicationDate(LocalDate.now().minusDays(1));
        when(categoryPersistencePort.findById(existingCategory.getId())).thenReturn(Optional.of(existingCategory));
        when(locationPersistencePort.findById(existingLocation.getId())).thenReturn(Optional.of(existingLocation));
        assertThrows(InvalidPublicationDateException.class, () -> houseUseCase.save(validHouseModel, sellerRole, sellerId));
        verify(housePersistencePort, never()).save(any(HouseModel.class));
    }

    @Test
    void save_invalidPublicationDateTooFarInFuture_throwsInvalidPublicationDateException_whenRoleIsSeller() {
        validHouseModel.setActivePublicationDate(LocalDate.now().plusDays(DomainConstants.MAX_PUBLICATION_DAYS + 1));
        when(categoryPersistencePort.findById(existingCategory.getId())).thenReturn(Optional.of(existingCategory));
        when(locationPersistencePort.findById(existingLocation.getId())).thenReturn(Optional.of(existingLocation));
        assertThrows(InvalidPublicationDateException.class, () -> houseUseCase.save(validHouseModel, sellerRole, sellerId));
        verify(housePersistencePort, never()).save(any(HouseModel.class));
    }

    @Test
    void save_categoryNotFound_throwsCategoryNotFoundException_whenRoleIsSeller() {
        when(categoryPersistencePort.findById(existingCategory.getId())).thenReturn(Optional.empty());
        assertThrows(HouseCategoryNotFoundException.class, () -> houseUseCase.save(validHouseModel, sellerRole, sellerId));
        verify(housePersistencePort, never()).save(any(HouseModel.class));
    }

    @Test
    void save_locationNotFound_throwsLocationNotFoundException_whenRoleIsSeller() {
        when(categoryPersistencePort.findById(existingCategory.getId())).thenReturn(Optional.of(existingCategory));
        when(locationPersistencePort.findById(existingLocation.getId())).thenReturn(Optional.empty());
        assertThrows(HouseLocationNotFoundException.class, () -> houseUseCase.save(validHouseModel, sellerRole, sellerId));
        verify(housePersistencePort, never()).save(any(HouseModel.class));
    }

    @Test
    void save_validHouseModel_activePublicationDateIsNow_savesWithPublishedStatus_whenRoleIsSeller() {
        validHouseModel.setActivePublicationDate(LocalDate.now());

        when(categoryPersistencePort.findById(existingCategory.getId())).thenReturn(Optional.of(existingCategory));
        when(locationPersistencePort.findById(existingLocation.getId())).thenReturn(Optional.of(existingLocation));
        when(housePersistencePort.existsByNameAndLocationId(validHouseModel.getName(), existingLocation.getId())).thenReturn(false);

        houseUseCase.save(validHouseModel, sellerRole, sellerId);

        ArgumentCaptor<HouseModel> houseModelCaptor = ArgumentCaptor.forClass(HouseModel.class);
        verify(housePersistencePort, times(1)).save(houseModelCaptor.capture());
        HouseModel savedHouse = houseModelCaptor.getValue();

        assertEquals("Beautiful Apartment", savedHouse.getName());
        assertEquals(PublicationStatus.PUBLISHED, savedHouse.getStatus());
        assertEquals(LocalDate.now(), savedHouse.getPublicationDate());
    }

    @Test
    void save_houseNameNotUniqueInLocation_throwsHouseAlreadyExistsException_whenRoleIsSeller() {
        when(categoryPersistencePort.findById(existingCategory.getId())).thenReturn(Optional.of(existingCategory));
        when(locationPersistencePort.findById(existingLocation.getId())).thenReturn(Optional.of(existingLocation));
        when(housePersistencePort.existsByNameAndLocationId(validHouseModel.getName(), existingLocation.getId())).thenReturn(true);

        assertThrows(HouseAlreadyExistsException.class, () -> houseUseCase.save(validHouseModel, sellerRole, sellerId));
        verify(housePersistencePort, never()).save(any(HouseModel.class));
    }

    @Test
    void searchHouses_negativePageNumber_throwsPageNumberNegativeException() {
        HouseSearchCriteria criteria = new HouseSearchCriteria();
        criteria.setPage(DomainConstants.DEFAULT_PAGE_NUMBER - 1);
        assertThrows(PageNumberNegativeException.class, () -> houseUseCase.searchHouses(criteria, sellerRole, sellerId));
        verifyNoInteractions(housePersistencePort);
    }

    @Test
    void searchHouses_invalidPageSize_throwsPageSizeInvalidException() {
        HouseSearchCriteria criteria = new HouseSearchCriteria();
        criteria.setSize(DomainConstants.DEFAULT_SIZE_NUMBER);
        assertThrows(PageSizeInvalidException.class, () -> houseUseCase.searchHouses(criteria, sellerRole, sellerId));
        criteria.setSize(DomainConstants.DEFAULT_SIZE_NUMBER - 1);
        assertThrows(PageSizeInvalidException.class, () -> houseUseCase.searchHouses(criteria, sellerRole, sellerId));
        verifyNoInteractions(housePersistencePort);
    }

    @Test
    void searchHouses_minimumBedroomsNotMet_throwsMinimumBedroomsException() {
        HouseSearchCriteria criteria = new HouseSearchCriteria();
        criteria.setSize(DomainConstants.DEFAULT_SIZE_NUMBER + 1);
        criteria.setBedrooms(DomainConstants.MIN_BEDROOMS - 1);
        assertThrows(HouseMinimumBedroomsRequiredException.class, () -> houseUseCase.searchHouses(criteria, sellerRole, sellerId));
        verifyNoInteractions(housePersistencePort);
    }

    @Test
    void searchHouses_minimumBathroomsNotMet_throwsMinimumBathroomsException() {
        HouseSearchCriteria criteria = new HouseSearchCriteria();
        criteria.setSize(DomainConstants.DEFAULT_SIZE_NUMBER + 1);
        criteria.setBathrooms(DomainConstants.MIN_BATHROOMS - 1);
        assertThrows(HouseMinimumBathroomsRequiredException.class, () -> houseUseCase.searchHouses(criteria, sellerRole, sellerId));
        verifyNoInteractions(housePersistencePort);
    }

    @Test
    void searchHouses_minimumPriceNotMet_throwsMinimumPriceException() {
        HouseSearchCriteria criteria = new HouseSearchCriteria();
        criteria.setSize(DomainConstants.DEFAULT_SIZE_NUMBER + 1);
        criteria.setMinPrice(DomainConstants.MIN_PRICE);
        assertThrows(HouseMinimumRequirePriceException.class, () -> houseUseCase.searchHouses(criteria, sellerRole, sellerId));

        criteria.setMinPrice(DomainConstants.MIN_PRICE.subtract(BigDecimal.ONE));
        assertThrows(HouseMinimumRequirePriceException.class, () -> houseUseCase.searchHouses(criteria, sellerRole, sellerId));
        verifyNoInteractions(housePersistencePort);
    }

    @Test
    void searchHouses_invalidSortDirection_throwsInvalidSortDirectionException() {
        HouseSearchCriteria criteria = new HouseSearchCriteria();
        criteria.setSize(DomainConstants.DEFAULT_SIZE_NUMBER + 1);
        criteria.setSortDirection("invalid");
        assertThrows(InvalidSortDirectionException.class, () -> houseUseCase.searchHouses(criteria, sellerRole, sellerId));
        criteria.setSortDirection("ascc");
        assertThrows(InvalidSortDirectionException.class, () -> houseUseCase.searchHouses(criteria, sellerRole, sellerId));
        verifyNoInteractions(housePersistencePort);
    }

    @Test
    void searchHouses_invalidSortByField_throwsInvalidSortByFieldException() {
        HouseSearchCriteria criteria = new HouseSearchCriteria();
        criteria.setSize(DomainConstants.DEFAULT_SIZE_NUMBER + 1);
        criteria.setSortBy("invalidField");
        assertThrows(InvalidSortByFieldException.class, () -> houseUseCase.searchHouses(criteria, sellerRole, sellerId));
        verifyNoInteractions(housePersistencePort);
    }

    @Test
    void searchHouses_validSortByFieldPrice_callsPersistencePort() {
        HouseSearchCriteria criteria = new HouseSearchCriteria();
        criteria.setPage(1);
        criteria.setSize(10);
        criteria.setSortBy(DomainConstants.SORT_BY_PRICE);
        when(housePersistencePort.search(ArgumentMatchers.any(HouseSearchCriteria.class))).thenReturn(new PageResult<>(Collections.emptyList(), 0L, 1, 10, 1, true, true));
        houseUseCase.searchHouses(criteria, sellerRole, sellerId);
        verify(housePersistencePort, times(1)).search(criteria);
    }

    @Test
    void searchHouses_validSortByFieldBedrooms_callsPersistencePort() {
        HouseSearchCriteria criteria = new HouseSearchCriteria();
        criteria.setPage(1);
        criteria.setSize(10);
        criteria.setSortBy(DomainConstants.SORT_BY_BEDROOMS);
        when(housePersistencePort.search(ArgumentMatchers.any(HouseSearchCriteria.class))).thenReturn(new PageResult<>(Collections.emptyList(), 0L, 1, 10, 1, true, true));
        houseUseCase.searchHouses(criteria, sellerRole, sellerId);
        verify(housePersistencePort, times(1)).search(criteria);
    }

    @Test
    void searchHouses_validSortByFieldBathrooms_callsPersistencePort() {
        HouseSearchCriteria criteria = new HouseSearchCriteria();
        criteria.setPage(1);
        criteria.setSize(10);
        criteria.setSortBy(DomainConstants.SORT_BY_BATHROOMS);
        when(housePersistencePort.search(ArgumentMatchers.any(HouseSearchCriteria.class))).thenReturn(new PageResult<>(Collections.emptyList(), 0L, 1, 10, 1, true, true));
        houseUseCase.searchHouses(criteria, sellerRole, sellerId);
        verify(housePersistencePort, times(1)).search(criteria);
    }

    @Test
    void searchHouses_validSortByFieldPublicationDate_callsPersistencePort() {
        HouseSearchCriteria criteria = new HouseSearchCriteria();
        criteria.setPage(1);
        criteria.setSize(10);
        criteria.setSortBy(DomainConstants.SORT_BY_PUBLICATION_DATE);
        when(housePersistencePort.search(ArgumentMatchers.any(HouseSearchCriteria.class))).thenReturn(new PageResult<>(Collections.emptyList(), 0L, 1, 10, 1, true, true));
        houseUseCase.searchHouses(criteria, sellerRole, sellerId);
        verify(housePersistencePort, times(1)).search(criteria);
    }

    @Test
    void searchHouses_validSortByFieldCategory_callsPersistencePort() {
        HouseSearchCriteria criteria = new HouseSearchCriteria();
        criteria.setPage(1);
        criteria.setSize(10);
        criteria.setSortBy(DomainConstants.SORT_BY_CATEGORY);
        when(housePersistencePort.search(ArgumentMatchers.any(HouseSearchCriteria.class))).thenReturn(new PageResult<>(Collections.emptyList(), 0L, 1, 10, 1, true, true));
        houseUseCase.searchHouses(criteria, sellerRole, sellerId);
        verify(housePersistencePort, times(1)).search(criteria);
    }

    @Test
    void searchHouses_validCriteria_callsPersistencePort() {
        HouseSearchCriteria criteria = new HouseSearchCriteria();
        criteria.setPage(1);
        criteria.setSize(10);
        when(housePersistencePort.search(ArgumentMatchers.any(HouseSearchCriteria.class))).thenReturn(new PageResult<>(Collections.emptyList(), 0L, 1, 10, 1, true, true));
        houseUseCase.searchHouses(criteria, sellerRole, sellerId);
        verify(housePersistencePort, times(1)).search(criteria);
    }

    @Test
    void searchHouses_adminRole_statusIsNull() {
        HouseSearchCriteria criteria = new HouseSearchCriteria();
        criteria.setPage(1);
        criteria.setSize(10);

        when(housePersistencePort.search(ArgumentMatchers.any(HouseSearchCriteria.class))).thenReturn(new PageResult<>(Collections.emptyList(), 0L, 1, 10, 1, true, true));

        houseUseCase.searchHouses(criteria, adminRole, otherUserId);

        ArgumentCaptor<HouseSearchCriteria> criteriaCaptor = ArgumentCaptor.forClass(HouseSearchCriteria.class);
        verify(housePersistencePort).search(criteriaCaptor.capture());
        assertNull(criteriaCaptor.getValue().getStatus());
        assertNull(criteriaCaptor.getValue().getPublisherId());
    }

    @Test
    void searchHouses_anonymousRole_statusIsPublished() {
        HouseSearchCriteria criteria = new HouseSearchCriteria();
        criteria.setPage(1);
        criteria.setSize(10);

        when(housePersistencePort.search(ArgumentMatchers.any(HouseSearchCriteria.class))).thenReturn(new PageResult<>(Collections.emptyList(), 0L, 1, 10, 1, true, true));

        houseUseCase.searchHouses(criteria, anonymousRole, null);

        ArgumentCaptor<HouseSearchCriteria> criteriaCaptor = ArgumentCaptor.forClass(HouseSearchCriteria.class);
        verify(housePersistencePort).search(criteriaCaptor.capture());
        assertEquals(PublicationStatus.PUBLISHED, criteriaCaptor.getValue().getStatus());
        assertNull(criteriaCaptor.getValue().getPublisherId());
    }

    @Test
    void searchHouses_sellerRole_statusIsNullAndPublisherIdIsSet() {
        HouseSearchCriteria criteria = new HouseSearchCriteria();
        criteria.setPage(1);
        criteria.setSize(10);

        when(housePersistencePort.search(ArgumentMatchers.any(HouseSearchCriteria.class))).thenReturn(new PageResult<>(Collections.emptyList(), 0L, 1, 10, 1, true, true));

        houseUseCase.searchHouses(criteria, sellerRole, sellerId);

        ArgumentCaptor<HouseSearchCriteria> criteriaCaptor = ArgumentCaptor.forClass(HouseSearchCriteria.class);
        verify(housePersistencePort).search(criteriaCaptor.capture());
        assertNull(criteriaCaptor.getValue().getStatus());
        assertEquals(sellerId, criteriaCaptor.getValue().getPublisherId());
    }

    @Test
    void searchHouses_otherRole_statusIsPublished() {
        HouseSearchCriteria criteria = new HouseSearchCriteria();
        criteria.setPage(1);
        criteria.setSize(10);

        when(housePersistencePort.search(ArgumentMatchers.any(HouseSearchCriteria.class))).thenReturn(new PageResult<>(Collections.emptyList(), 0L, 1, 10, 1, true, true));

        houseUseCase.searchHouses(criteria, buyerRole, otherUserId);

        ArgumentCaptor<HouseSearchCriteria> criteriaCaptor = ArgumentCaptor.forClass(HouseSearchCriteria.class);
        verify(housePersistencePort).search(criteriaCaptor.capture());
        assertEquals(PublicationStatus.PUBLISHED, criteriaCaptor.getValue().getStatus());
        assertNull(criteriaCaptor.getValue().getPublisherId());
    }

    @Test
    void findById_validId_returnsHouseModel() {
        Long houseId = 1L;
        HouseModel expectedHouse = new HouseModel(
                houseId,
                "Name",
                "Valid Description",
                existingCategory,
                3,
                2,
                new BigDecimal("100"),
                existingLocation,
                LocalDate.now(),
                LocalDate.now(),
                PublicationStatus.PUBLISHED,
                sellerId);
        when(housePersistencePort.findById(houseId)).thenReturn(expectedHouse);

        HouseModel result = houseUseCase.findById(houseId);

        assertNotNull(result);
        assertEquals(houseId, result.getId());
        verify(housePersistencePort, times(1)).findById(houseId);
    }

    @Test
    void findPublisherIdById_validId_returnsPublisherId() {
        Long houseId = 1L;
        Long expectedPublisherId = 123L;
        when(housePersistencePort.findPublisherIdById(houseId)).thenReturn(expectedPublisherId);

        Long result = houseUseCase.findPublisherIdById(houseId);

        assertNotNull(result);
        assertEquals(expectedPublisherId, result);
        verify(housePersistencePort, times(1)).findPublisherIdById(houseId);
    }

    @Test
    void findAllByPublisherId_validPublisherId_returnsListOfHouses() {
        Long publisherId = 123L;
        List<HouseModel> expectedHouses = List.of(
                new HouseModel(1L, "House A", "Desc", existingCategory, 2, 1, BigDecimal.valueOf(100000), existingLocation, LocalDate.now(), LocalDate.now(), PublicationStatus.PUBLISHED, publisherId),
                new HouseModel(2L, "House B", "Desc", existingCategory, 3, 2, BigDecimal.valueOf(200000), existingLocation, LocalDate.now(), LocalDate.now(), PublicationStatus.PUBLISHED, publisherId)
        );
        when(housePersistencePort.findAllByPublisherId(publisherId)).thenReturn(expectedHouses);

        List<HouseModel> result = houseUseCase.findAllByPublisherId(publisherId);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        verify(housePersistencePort, times(1)).findAllByPublisherId(publisherId);
    }

    @Test
    void findIdsByCityIdAndSector_validCriteria_returnsListOfIds() {
        Long cityId = 1L;
        String sector = "Sector A";
        List<Long> expectedIds = List.of(10L, 20L, 30L);
        when(housePersistencePort.findIdsByCityIdAndSector(cityId, sector)).thenReturn(expectedIds);

        List<Long> result = houseUseCase.findIdsByCityIdAndSector(cityId, sector);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(3, result.size());
        verify(housePersistencePort, times(1)).findIdsByCityIdAndSector(cityId, sector);
    }
}