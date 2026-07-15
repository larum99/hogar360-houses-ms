package com.hogar360.houses.houses.domain.usecases;

import com.hogar360.houses.houses.domain.exceptions.*;
import com.hogar360.houses.houses.domain.model.CityModel;
import com.hogar360.houses.houses.domain.model.DepartmentModel;
import com.hogar360.houses.houses.domain.model.LocationModel;
import com.hogar360.houses.houses.domain.utils.PageResult;
import com.hogar360.houses.houses.domain.ports.out.CityPersistencePort;
import com.hogar360.houses.houses.domain.ports.out.LocationPersistencePort;
import com.hogar360.houses.houses.domain.utils.constants.DomainConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationUseCaseTest {

    @Mock
    private CityPersistencePort cityPersistencePort;

    @Mock
    private LocationPersistencePort locationPersistencePort;

    @InjectMocks
    private LocationUseCase locationUseCase;

    private CityModel cityModel;
    private Long cityId = 1L;
    private String validSector = "Neighborhood A";
    private String adminRole;
    private String userRole;

    @BeforeEach
    void setUp() {
        DepartmentModel dummyDepartment = new DepartmentModel();
        cityModel = new CityModel(cityId, "Test City", null, dummyDepartment);
        adminRole = DomainConstants.ROLE_ADMIN;
        userRole = "USER";
    }

    @Test
    void createLocation_shouldCreateLocation_whenCityExistsAndSectorIsValidAndRoleIsAdmin() {
        when(cityPersistencePort.getCityById(cityId)).thenReturn(Optional.of(cityModel));
        when(locationPersistencePort.getBySectorAndCityId(validSector, cityId)).thenReturn(Optional.empty());
        LocationModel expectedLocation = new LocationModel(null, cityModel, validSector);
        when(locationPersistencePort.save(any(LocationModel.class))).thenReturn(expectedLocation);

        LocationModel actualLocation = locationUseCase.createLocation(cityId, validSector, adminRole);

        assertEquals(expectedLocation.getCity(), actualLocation.getCity());
        assertEquals(expectedLocation.getSector(), actualLocation.getSector());
        verify(cityPersistencePort, times(1)).getCityById(cityId);
        verify(locationPersistencePort, times(1)).getBySectorAndCityId(validSector, cityId);
        verify(locationPersistencePort, times(1)).save(any(LocationModel.class));
    }

    @Test
    void createLocation_shouldThrowForbiddenException_whenRoleIsNotAdmin() {
        assertThrows(ForbiddenException.class, () -> locationUseCase.createLocation(cityId, validSector, userRole));

        verifyNoInteractions(cityPersistencePort);
        verifyNoInteractions(locationPersistencePort);
    }

    @Test
    void createLocation_shouldThrowCityNotFoundException_whenCityDoesNotExistAndRoleIsAdmin() {
        when(cityPersistencePort.getCityById(cityId)).thenReturn(Optional.empty());

        assertThrows(CityNotFoundException.class, () -> locationUseCase.createLocation(cityId, validSector, adminRole));

        verify(cityPersistencePort, times(1)).getCityById(cityId);
        verify(locationPersistencePort, never()).getBySectorAndCityId(anyString(), anyLong());
        verify(locationPersistencePort, never()).save(any());
    }

    @Test
    void createLocation_shouldThrowLocationSectorMaxSizeExceededException_whenSectorIsTooLongAndRoleIsAdmin() {
        when(cityPersistencePort.getCityById(cityId)).thenReturn(Optional.of(cityModel));
        String longSector = "a".repeat(DomainConstants.MAX_SECTOR_LENGTH + 1);

        assertThrows(LocationSectorMaxSizeExceededException.class, () -> locationUseCase.createLocation(cityId, longSector, adminRole));

        verify(cityPersistencePort, times(1)).getCityById(cityId);
        verify(locationPersistencePort, never()).getBySectorAndCityId(anyString(), anyLong());
        verify(locationPersistencePort, never()).save(any());
    }

    @Test
    void createLocation_shouldCreateLocation_whenSectorIsNullAndRoleIsAdmin() {
        when(cityPersistencePort.getCityById(cityId)).thenReturn(Optional.of(cityModel));
        when(locationPersistencePort.getBySectorAndCityId(null, cityId)).thenReturn(Optional.empty());

        LocationModel expectedLocation = new LocationModel(null, cityModel, null);
        when(locationPersistencePort.save(any(LocationModel.class))).thenReturn(expectedLocation);

        LocationModel actualLocation = locationUseCase.createLocation(cityId, null, adminRole);

        assertEquals(expectedLocation.getCity(), actualLocation.getCity());
        assertEquals(expectedLocation.getSector(), actualLocation.getSector());
        verify(cityPersistencePort, times(1)).getCityById(cityId);
        verify(locationPersistencePort, times(1)).getBySectorAndCityId(null, cityId);
        verify(locationPersistencePort, times(1)).save(any(LocationModel.class));
    }

    @Test
    void createLocation_shouldThrowLocationAlreadyExistsException_whenSectorAlreadyExistsInCity() {
        when(cityPersistencePort.getCityById(cityId)).thenReturn(Optional.of(cityModel));
        when(locationPersistencePort.getBySectorAndCityId(validSector, cityId))
                .thenReturn(Optional.of(new LocationModel(2L, cityModel, validSector)));

        assertThrows(LocationAlreadyExistsException.class, () -> locationUseCase.createLocation(cityId, validSector, adminRole));

        verify(cityPersistencePort, times(1)).getCityById(cityId);
        verify(locationPersistencePort, times(1)).getBySectorAndCityId(validSector, cityId);
        verify(locationPersistencePort, never()).save(any());
    }

    @Test
    void searchLocations_shouldReturnPageResult_whenValidParametersAreProvided() {
        String searchTerm = "Test";
        int page = 0;
        int size = 10;
        String sortBy = "name";
        String sortDirection = "asc";
        PageResult<LocationModel> expectedResult = new PageResult<>(Collections.emptyList(), 0L, 0, 0, 10, true, true);
        when(locationPersistencePort.searchLocations(searchTerm, page, size, sortBy, sortDirection)).thenReturn(expectedResult);

        PageResult<LocationModel> actualResult = locationUseCase.searchLocations(searchTerm, page, size, sortBy, sortDirection);

        assertEquals(expectedResult, actualResult);
        verify(locationPersistencePort, times(1)).searchLocations(searchTerm, page, size, sortBy, sortDirection);
    }

    @Test
    void searchLocations_shouldThrowPageNumberNegativeException_whenPageIsNegative() {
        String searchTerm = "Test";
        int page = -1;
        int size = 10;
        String sortBy = "name";
        String sortDirection = "asc";

        assertThrows(PageNumberNegativeException.class, () -> locationUseCase.searchLocations(searchTerm, page, size, sortBy, sortDirection));

        verify(locationPersistencePort, never()).searchLocations(anyString(), anyInt(), anyInt(), anyString(), anyString());
    }

    @Test
    void searchLocations_shouldThrowPageSizeInvalidException_whenSizeIsInvalid() {
        int page = 0;
        int size = DomainConstants.DEFAULT_SIZE_NUMBER;
        String searchTerm = "Test";
        String sortBy = "name";
        String sortDirection = "asc";

        assertThrows(PageSizeInvalidException.class, () -> locationUseCase.searchLocations(searchTerm, page, size, sortBy, sortDirection));

        verify(locationPersistencePort, never()).searchLocations(anyString(), anyInt(), anyInt(), anyString(), anyString());
    }

    @Test
    void findByCityId_shouldReturnLocations_whenCityExists() {
        Long cityIdToFind = 1L;
        List<LocationModel> expectedLocations = Arrays.asList(
                new LocationModel(1L, cityModel, "Sector 1"),
                new LocationModel(2L, cityModel, "Sector 2")
        );
        when(cityPersistencePort.getCityById(cityId)).thenReturn(Optional.of(cityModel));
        when(locationPersistencePort.findByCityId(cityIdToFind)).thenReturn(expectedLocations);

        List<LocationModel> actualLocations = locationUseCase.findByCityId(cityIdToFind);

        assertEquals(expectedLocations, actualLocations);
        verify(cityPersistencePort, times(1)).getCityById(cityIdToFind);
        verify(locationPersistencePort, times(1)).findByCityId(cityIdToFind);
    }

    @Test
    void findByCityId_shouldThrowCityNotFoundException_whenCityDoesNotExist() {
        Long nonExistentCityId = 99L;
        when(cityPersistencePort.getCityById(nonExistentCityId)).thenReturn(Optional.empty());

        assertThrows(CityNotFoundException.class, () -> locationUseCase.findByCityId(nonExistentCityId));

        verify(cityPersistencePort, times(1)).getCityById(nonExistentCityId);
        verify(locationPersistencePort, never()).findByCityId(anyLong());
    }
}