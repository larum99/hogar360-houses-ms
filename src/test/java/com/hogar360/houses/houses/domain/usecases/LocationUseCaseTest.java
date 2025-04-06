package com.hogar360.houses.houses.domain.usecases;

import com.hogar360.houses.houses.domain.exceptions.CityNotFoundException;
import com.hogar360.houses.houses.domain.exceptions.LocationSectorMaxSizeExceededException;
import com.hogar360.houses.houses.domain.exceptions.PageNumberNegativeException;
import com.hogar360.houses.houses.domain.exceptions.PageSizeInvalidException;
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

    @BeforeEach
    void setUp() {
        DepartmentModel dummyDepartment = new DepartmentModel();
        cityModel = new CityModel(cityId, "Test City", null, dummyDepartment);
    }

    @Test
    void createLocation_shouldCreateLocation_whenCityExistsAndSectorIsValid() {
        when(cityPersistencePort.getCityById(cityId)).thenReturn(cityModel);
        LocationModel expectedLocation = new LocationModel(null, cityModel, validSector);
        when(locationPersistencePort.save(any(LocationModel.class))).thenReturn(expectedLocation);

        LocationModel actualLocation = locationUseCase.createLocation(cityId, validSector);

        assertEquals(expectedLocation.getCity(), actualLocation.getCity());
        assertEquals(expectedLocation.getSector(), actualLocation.getSector());
        verify(cityPersistencePort, times(1)).getCityById(cityId);
        verify(locationPersistencePort, times(1)).save(any(LocationModel.class));
    }

    @Test
    void createLocation_shouldThrowCityNotFoundException_whenCityDoesNotExist() {
        when(cityPersistencePort.getCityById(cityId)).thenReturn(null);

        assertThrows(CityNotFoundException.class, () -> locationUseCase.createLocation(cityId, validSector));

        verify(cityPersistencePort, times(1)).getCityById(cityId);
        verify(locationPersistencePort, never()).save(any());
    }

    @Test
    void createLocation_shouldThrowLocationSectorMaxSizeExceededException_whenSectorIsTooLong() {
        when(cityPersistencePort.getCityById(cityId)).thenReturn(cityModel);
        String longSector = "a".repeat(DomainConstants.MAX_SECTOR_LENGTH + 1);

        assertThrows(LocationSectorMaxSizeExceededException.class, () -> locationUseCase.createLocation(cityId, longSector));

        verify(cityPersistencePort, times(1)).getCityById(cityId);
        verify(locationPersistencePort, never()).save(any());
    }

    @Test
    void createLocation_shouldCreateLocation_whenSectorIsNull() {
        when(cityPersistencePort.getCityById(cityId)).thenReturn(cityModel);
        LocationModel expectedLocation = new LocationModel(null, cityModel, null);
        when(locationPersistencePort.save(any(LocationModel.class))).thenReturn(expectedLocation);

        LocationModel actualLocation = locationUseCase.createLocation(cityId, null);

        assertEquals(expectedLocation.getCity(), actualLocation.getCity());
        assertEquals(expectedLocation.getSector(), actualLocation.getSector());
        verify(cityPersistencePort, times(1)).getCityById(cityId);
        verify(locationPersistencePort, times(1)).save(any(LocationModel.class));
    }

    @Test
    void searchLocations_shouldReturnPageResult_whenValidParametersAreProvided() {
        String searchTerm = "Test";
        int page = 0;
        int size = 10;
        String sortBy = "name";
        String sortDirection = "asc";
        // Assuming PageResult constructor takes (content, totalElements, totalPages, currentPage, pageSize, isFirst, isLast)
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
        String searchTerm = "Test";
        int page = 0;
        int size = DomainConstants.DEFAULT_SIZE_NUMBER;
        String sortBy = "name";
        String sortDirection = "asc";

        assertThrows(PageSizeInvalidException.class, () -> locationUseCase.searchLocations(searchTerm, page, size, sortBy, sortDirection));

        verify(locationPersistencePort, never()).searchLocations(anyString(), anyInt(), anyInt(), anyString(), anyString());
    }
}