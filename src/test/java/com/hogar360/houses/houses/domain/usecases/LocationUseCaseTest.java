package com.hogar360.houses.houses.domain.usecases;

import com.hogar360.houses.houses.domain.exceptions.CityNotFoundException;
import com.hogar360.houses.houses.domain.exceptions.LocationSectorMaxSizeExceededException;
import com.hogar360.houses.houses.domain.model.CityModel;
import com.hogar360.houses.houses.domain.model.DepartmentModel;
import com.hogar360.houses.houses.domain.model.LocationModel;
import com.hogar360.houses.houses.domain.utils.PageResult;
import com.hogar360.houses.houses.domain.ports.out.CityPersistencePort;
import com.hogar360.houses.houses.domain.ports.out.LocationPersistencePort;
import com.hogar360.houses.houses.domain.utils.constants.DomainConstants;
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

    @Test
    void createLocation_validInput_shouldReturnSavedLocation() {

        Long cityId = 1L;
        String sector = "My Sector";

        DepartmentModel department = new DepartmentModel(1L, "Department Name", "Department Description");

        CityModel city = new CityModel(cityId, "City Name", "Description", department);

        LocationModel savedLocation = new LocationModel(1L, city, sector);

        when(cityPersistencePort.getCityById(cityId)).thenReturn(city);
        when(locationPersistencePort.save(any(LocationModel.class))).thenReturn(savedLocation);

        LocationModel result = locationUseCase.createLocation(cityId, sector);

        assertNotNull(result);
        assertEquals(savedLocation.getId(), result.getId());
        assertEquals(savedLocation.getCity(), result.getCity());
        assertEquals(savedLocation.getSector(), result.getSector());
        verify(cityPersistencePort, times(1)).getCityById(cityId);
        verify(locationPersistencePort, times(1)).save(any(LocationModel.class));
    }

    @Test
    void createLocation_cityNotFound_shouldThrowCityNotFoundException() {

        Long cityId = 1L;
        String sector = "My Sector";

        when(cityPersistencePort.getCityById(cityId)).thenReturn(null);

        assertThrows(CityNotFoundException.class, () -> locationUseCase.createLocation(cityId, sector));
        verify(cityPersistencePort, times(1)).getCityById(cityId);
        verify(locationPersistencePort, never()).save(any());
    }

    @Test
    void createLocation_sectorTooLong_shouldThrowLocationSectorMaxSizeExceededException() {

        Long cityId = 1L;
        String sector = "This is a sector name that is much longer than the maximum allowed length of " + DomainConstants.MAX_SECTOR_LENGTH;
        String cityName = "City Name";
        String cityDescription = "Description";

        DepartmentModel department = new DepartmentModel(1L, "Department Name", "Department Description");

        CityModel city = new CityModel(cityId, cityName, cityDescription, department);

        when(cityPersistencePort.getCityById(cityId)).thenReturn(city);

        assertThrows(LocationSectorMaxSizeExceededException.class, () -> locationUseCase.createLocation(cityId, sector));
        verify(cityPersistencePort, times(1)).getCityById(cityId);
        verify(locationPersistencePort, never()).save(any());
    }

    @Test
    void searchLocations_shouldCallPersistencePortWithCorrectParameters() {
        String searchTerm = "test";
        int page = 0;
        int size = 10;
        String sortBy = "city.name";
        String sortDirection = "asc";

        PageResult<LocationModel> expectedPage = new PageResult<>(Collections.emptyList(), 0, 0, 0, 0, true, true);

        when(locationPersistencePort.searchLocations(searchTerm, page, size, sortBy, sortDirection)).thenReturn(expectedPage);

        PageResult<LocationModel> result = locationUseCase.searchLocations(searchTerm, page, size, sortBy, sortDirection);

        assertNotNull(result);
        assertEquals(expectedPage, result);
        verify(locationPersistencePort, times(1)).searchLocations(searchTerm, page, size, sortBy, sortDirection);
    }
}
