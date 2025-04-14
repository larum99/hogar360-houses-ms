package com.hogar360.houses.houses.domain.usecases;

import com.hogar360.houses.houses.domain.exceptions.*;
import com.hogar360.houses.houses.domain.model.*;
import com.hogar360.houses.houses.domain.ports.out.HousePersistencePort;
import com.hogar360.houses.houses.domain.ports.out.CategoryPersistencePort;
import com.hogar360.houses.houses.domain.ports.out.LocationPersistencePort;
import com.hogar360.houses.houses.domain.utils.PublicationStatus;
import com.hogar360.houses.houses.domain.utils.constants.DomainConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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

    @InjectMocks
    private HouseUseCase houseUseCase;

    private HouseModel validHouseModel;
    private CategoryModel existingCategory;
    private LocationModel existingLocation;
    private DepartmentModel department;
    private CityModel city;

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
    }

    @Test
    void save_validHouseModel_savesSuccessfully() {
        when(categoryPersistencePort.findById(existingCategory.getId())).thenReturn(Optional.of(existingCategory));
        when(locationPersistencePort.findById(existingLocation.getId())).thenReturn(Optional.of(existingLocation));

        houseUseCase.save(validHouseModel);

        ArgumentCaptor<HouseModel> houseModelCaptor = ArgumentCaptor.forClass(HouseModel.class);
        verify(housePersistencePort, times(1)).save(houseModelCaptor.capture());
        HouseModel savedHouse = houseModelCaptor.getValue();

        assertEquals("Beautiful Apartment", savedHouse.getName());
        assertEquals(PublicationStatus.PAUSED, savedHouse.getStatus());
        assertEquals(LocalDate.now(), savedHouse.getPublicationDate());
    }

    @Test
    void save_validHouseModel_futurePublicationDate_savesWithPausedStatus() {
        LocalDate futureDate = LocalDate.now().plusDays(10);
        validHouseModel.setActivePublicationDate(futureDate);
        when(categoryPersistencePort.findById(existingCategory.getId())).thenReturn(Optional.of(existingCategory));
        when(locationPersistencePort.findById(existingLocation.getId())).thenReturn(Optional.of(existingLocation));

        houseUseCase.save(validHouseModel);

        ArgumentCaptor<HouseModel> houseModelCaptor = ArgumentCaptor.forClass(HouseModel.class);
        verify(housePersistencePort, times(1)).save(houseModelCaptor.capture());
        assertEquals(PublicationStatus.PAUSED, houseModelCaptor.getValue().getStatus());
    }

    @Test
    void save_nullName_throwsRequiredFieldException() {
        HouseModel houseModelWithoutName = new HouseModel(null, null, "Desc", existingCategory, 3, 2, new BigDecimal("100"), existingLocation, LocalDate.now(), LocalDate.now(), PublicationStatus.PUBLISHED);
        assertThrows(NullPointerException.class, () -> houseUseCase.save(houseModelWithoutName), DomainConstants.FIELD_NAME_NULL_MESSAGE);
        verifyNoInteractions(housePersistencePort);
        verifyNoInteractions(categoryPersistencePort);
        verifyNoInteractions(locationPersistencePort);
    }

    @Test
    void save_nullDescription_throwsRequiredFieldException() {
        HouseModel houseModelWithoutDescription = new HouseModel(null, "Name", null, existingCategory, 3, 2, new BigDecimal("100"), existingLocation, LocalDate.now(), LocalDate.now(), PublicationStatus.PUBLISHED);
        assertThrows(NullPointerException.class, () -> houseUseCase.save(houseModelWithoutDescription), DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
        verifyNoInteractions(housePersistencePort);
        verifyNoInteractions(categoryPersistencePort);
        verifyNoInteractions(locationPersistencePort);
    }

    @Test
    void save_nullCategory_throwsRequiredFieldException() {
        HouseModel houseModelWithoutCategory = new HouseModel(null, "Name", "Desc", null, 3, 2, new BigDecimal("100"), existingLocation, LocalDate.now(), LocalDate.now(), PublicationStatus.PUBLISHED);
        assertThrows(NullPointerException.class, () -> houseUseCase.save(houseModelWithoutCategory), DomainConstants.FIELD_CATEGORY_NULL_MESSAGE);
        verifyNoInteractions(housePersistencePort);
        verifyNoInteractions(categoryPersistencePort);
        verifyNoInteractions(locationPersistencePort);
    }

    @Test
    void save_nullPrice_throwsRequiredFieldException() {
        HouseModel houseModelWithoutPrice = new HouseModel(null, "Name", "Desc", existingCategory, 3, 2, null, existingLocation, LocalDate.now(), LocalDate.now(), PublicationStatus.PUBLISHED);
        assertThrows(NullPointerException.class, () -> houseUseCase.save(houseModelWithoutPrice), DomainConstants.FIELD_PRICE_NULL_MESSAGE);
        verifyNoInteractions(housePersistencePort);
        verifyNoInteractions(categoryPersistencePort);
        verifyNoInteractions(locationPersistencePort);
    }

    @Test
    void save_nullLocation_throwsRequiredFieldException() {
        HouseModel houseModelWithoutLocation = new HouseModel(null, "Name", "Desc", existingCategory, 3, 2, new BigDecimal("100"), null, LocalDate.now(), LocalDate.now(), PublicationStatus.PUBLISHED);
        assertThrows(NullPointerException.class, () -> houseUseCase.save(houseModelWithoutLocation), DomainConstants.FIELD_LOCATION_NULL_MESSAGE);
        verifyNoInteractions(housePersistencePort);
        verifyNoInteractions(categoryPersistencePort);
        verifyNoInteractions(locationPersistencePort);
    }

    @Test
    void save_nullActivePublicationDate_throwsRequiredFieldException() {
        HouseModel houseModelWithoutActivePublicationDate = new HouseModel(null, "Name", "Desc", existingCategory, 3, 2, new BigDecimal("100"), existingLocation, LocalDate.now(), null, PublicationStatus.PUBLISHED);
        assertThrows(NullPointerException.class, () -> houseUseCase.save(houseModelWithoutActivePublicationDate), DomainConstants.FIELD_ACTIVE_PUBLICATION_DATE_NULL_MESSAGE);
        verifyNoInteractions(housePersistencePort);
        verifyNoInteractions(categoryPersistencePort);
        verifyNoInteractions(locationPersistencePort);
    }

    @Test
    void save_minimumBedroomsNotMet_throwsMinimumBedroomsException() {
        validHouseModel.setBedrooms(DomainConstants.MIN_BEDROOMS - 1);
        when(categoryPersistencePort.findById(existingCategory.getId())).thenReturn(Optional.of(existingCategory));
        when(locationPersistencePort.findById(existingLocation.getId())).thenReturn(Optional.of(existingLocation));
        assertThrows(HouseMinimumBedroomsRequiredException.class, () -> houseUseCase.save(validHouseModel));
        verifyNoInteractions(housePersistencePort);
    }

    @Test
    void save_minimumBathroomsNotMet_throwsMinimumBathroomsException() {
        validHouseModel.setBathrooms(DomainConstants.MIN_BATHROOMS - 1);
        when(categoryPersistencePort.findById(existingCategory.getId())).thenReturn(Optional.of(existingCategory));
        when(locationPersistencePort.findById(existingLocation.getId())).thenReturn(Optional.of(existingLocation));
        assertThrows(HouseMinimumBathroomsRequiredException.class, () -> houseUseCase.save(validHouseModel));
        verifyNoInteractions(housePersistencePort);
    }

    @Test
    void save_minimumPriceNotMet_throwsMinimumPriceException() {
        validHouseModel.setPrice(DomainConstants.MIN_PRICE);
        when(categoryPersistencePort.findById(existingCategory.getId())).thenReturn(Optional.of(existingCategory));
        when(locationPersistencePort.findById(existingLocation.getId())).thenReturn(Optional.of(existingLocation));
        assertThrows(HouseMinimumRequirePriceException.class, () -> houseUseCase.save(validHouseModel));
        validHouseModel.setPrice(DomainConstants.MIN_PRICE.subtract(BigDecimal.ONE));
        assertThrows(HouseMinimumRequirePriceException.class, () -> houseUseCase.save(validHouseModel));
        verifyNoInteractions(housePersistencePort);
    }

    @Test
    void save_invalidPublicationDateInPast_throwsInvalidPublicationDateException() {
        validHouseModel.setActivePublicationDate(LocalDate.now().minusDays(1));
        when(categoryPersistencePort.findById(existingCategory.getId())).thenReturn(Optional.of(existingCategory));
        when(locationPersistencePort.findById(existingLocation.getId())).thenReturn(Optional.of(existingLocation));
        assertThrows(InvalidPublicationDateException.class, () -> houseUseCase.save(validHouseModel));
        verifyNoInteractions(housePersistencePort);
    }

    @Test
    void save_invalidPublicationDateTooFarInFuture_throwsInvalidPublicationDateException() {
        validHouseModel.setActivePublicationDate(LocalDate.now().plusDays(DomainConstants.MAX_PUBLICATION_DAYS + 1));
        when(categoryPersistencePort.findById(existingCategory.getId())).thenReturn(Optional.of(existingCategory));
        when(locationPersistencePort.findById(existingLocation.getId())).thenReturn(Optional.of(existingLocation));
        assertThrows(InvalidPublicationDateException.class, () -> houseUseCase.save(validHouseModel));
        verifyNoInteractions(housePersistencePort);
    }

    @Test
    void save_categoryNotFound_throwsCategoryNotFoundException() {
        when(categoryPersistencePort.findById(existingCategory.getId())).thenReturn(Optional.empty());
        assertThrows(HouseCategoryNotFoundException.class, () -> houseUseCase.save(validHouseModel));
        verifyNoInteractions(housePersistencePort);
    }

    @Test
    void save_locationNotFound_throwsLocationNotFoundException() {
        when(categoryPersistencePort.findById(existingCategory.getId())).thenReturn(Optional.of(existingCategory));
        when(locationPersistencePort.findById(existingLocation.getId())).thenReturn(Optional.empty());
        assertThrows(HouseLocationNotFoundException.class, () -> houseUseCase.save(validHouseModel));
        verifyNoInteractions(housePersistencePort);
    }

    @Test
    void save_validHouseModel_activePublicationDateIsNow_savesWithPublishedStatus() {

        validHouseModel.setActivePublicationDate(LocalDate.now());

        when(categoryPersistencePort.findById(existingCategory.getId())).thenReturn(Optional.of(existingCategory));
        when(locationPersistencePort.findById(existingLocation.getId())).thenReturn(Optional.of(existingLocation));

        houseUseCase.save(validHouseModel);

        ArgumentCaptor<HouseModel> houseModelCaptor = ArgumentCaptor.forClass(HouseModel.class);
        verify(housePersistencePort, times(1)).save(houseModelCaptor.capture());
        HouseModel savedHouse = houseModelCaptor.getValue();

        assertEquals("Beautiful Apartment", savedHouse.getName());
        assertEquals(PublicationStatus.PUBLISHED, savedHouse.getStatus());
        assertEquals(LocalDate.now(), savedHouse.getPublicationDate());
    }
}