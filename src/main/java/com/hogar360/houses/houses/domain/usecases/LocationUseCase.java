package com.hogar360.houses.houses.domain.usecases;

import com.hogar360.houses.houses.domain.exceptions.*;
import com.hogar360.houses.houses.domain.model.CityModel;
import com.hogar360.houses.houses.domain.model.LocationModel;
import com.hogar360.houses.houses.domain.utils.PageResult;
import com.hogar360.houses.houses.domain.ports.in.LocationServicePort;
import com.hogar360.houses.houses.domain.ports.out.CityPersistencePort;
import com.hogar360.houses.houses.domain.ports.out.LocationPersistencePort;
import com.hogar360.houses.houses.domain.utils.constants.DomainConstants;

import java.util.List;

public class LocationUseCase implements LocationServicePort {
    private final CityPersistencePort cityPersistencePort;
    private final LocationPersistencePort locationPersistencePort;

    public LocationUseCase(CityPersistencePort cityPersistencePort, LocationPersistencePort locationPersistencePort) {
        this.cityPersistencePort = cityPersistencePort;
        this.locationPersistencePort = locationPersistencePort;
    }

    @Override
    public LocationModel createLocation(Long cityId, String sector, String role) {
        validateRole(role);
        CityModel city = validateAndGetCity(cityId);
        validateSectorLength(sector);
        checkIfSectorAlreadyExists(sector, cityId);

        LocationModel newLocation = new LocationModel(null, city, sector);

        return locationPersistencePort.save(newLocation);
    }

    @Override
    public PageResult<LocationModel> searchLocations(String searchTerm, int page, int size, String sortBy, String sortDirection) {
        validatePageNumber(page);
        validatePageSize(size);
        return locationPersistencePort.searchLocations(searchTerm, page, size, sortBy, sortDirection);
    }

    @Override
    public List<LocationModel> findByCityId(Long cityId) {

        CityModel city = validateAndGetCity(cityId);

        return locationPersistencePort.findByCityId(city.getId());
    }

    private void validateRole(String role) {
        if (!DomainConstants.ROLE_ADMIN.equals(role)) {
            throw new ForbiddenException();
        }
    }

    private CityModel validateAndGetCity(Long cityId) {
        CityModel city = cityPersistencePort.getCityById(cityId);
        if (city == null) {
            throw new CityNotFoundException();
        }
        return city;
    }

    private void validateSectorLength(String sector) {
        if (sector != null && sector.length() > DomainConstants.MAX_SECTOR_LENGTH) {
            throw new LocationSectorMaxSizeExceededException();
        }
    }

    private void checkIfSectorAlreadyExists(String sector, Long cityId) {
        LocationModel existingLocation = locationPersistencePort.getBySectorAndCityId(sector, cityId);
        if (existingLocation != null) {
            throw new LocationAlreadyExistsException();
        }
    }

    private void validatePageNumber(int page) {
        if (page < DomainConstants.DEFAULT_PAGE_NUMBER) {
            throw new PageNumberNegativeException();
        }
    }

    private void validatePageSize(int size) {
        if (size <= DomainConstants.DEFAULT_SIZE_NUMBER) {
            throw new PageSizeInvalidException();
        }
    }
}
