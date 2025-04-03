package com.hogar360.houses.houses.domain.usecases;

import com.hogar360.houses.houses.domain.exceptions.CityNotFoundException;
import com.hogar360.houses.houses.domain.exceptions.LocationSectorMaxSizeExceededException;
import com.hogar360.houses.houses.domain.model.CityModel;
import com.hogar360.houses.houses.domain.model.LocationModel;
import com.hogar360.houses.houses.domain.model.PageModel;
import com.hogar360.houses.houses.domain.ports.in.LocationServicePort;
import com.hogar360.houses.houses.domain.ports.out.CityPersistencePort;
import com.hogar360.houses.houses.domain.ports.out.LocationPersistencePort;
import com.hogar360.houses.houses.domain.utils.constants.DomainConstants;

public class LocationUseCase implements LocationServicePort {
    private final CityPersistencePort cityPersistencePort;
    private final LocationPersistencePort locationPersistencePort;

    public LocationUseCase(CityPersistencePort cityPersistencePort, LocationPersistencePort locationPersistencePort) {
        this.cityPersistencePort = cityPersistencePort;
        this.locationPersistencePort = locationPersistencePort;
    }

    @Override
    public LocationModel createLocation(Long cityId, String sector) {
        CityModel city = cityPersistencePort.getCityById(cityId);
        if (city == null) {
            throw new CityNotFoundException();
        }

        if (sector != null && sector.length() > DomainConstants.MAX_SECTOR_LENGTH) {
            throw new LocationSectorMaxSizeExceededException();
        }

        LocationModel newLocation = new LocationModel(null, city, sector);

        return locationPersistencePort.save(newLocation);
    }

    @Override
    public PageModel<LocationModel> searchLocations(String searchTerm, int page, int size, String sortBy, String sortDirection) {
        return locationPersistencePort.searchLocations(searchTerm, page, size, sortBy, sortDirection);
    }

}
