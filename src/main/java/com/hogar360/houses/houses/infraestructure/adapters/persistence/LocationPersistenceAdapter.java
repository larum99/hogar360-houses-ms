package com.hogar360.houses.houses.infraestructure.adapters.persistence;

import com.hogar360.houses.houses.domain.model.LocationModel;
import com.hogar360.houses.houses.domain.ports.out.LocationPersistencePort;
import com.hogar360.houses.houses.infraestructure.mappers.LocationEntityMapper;
import com.hogar360.houses.houses.infraestructure.repositories.mysql.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LocationPersistenceAdapter implements LocationPersistencePort {
    private final LocationRepository locationRepository;
    private final LocationEntityMapper locationEntityMapper;

    @Override
    public LocationModel save(LocationModel locationModel) {
        return locationEntityMapper.entityToModel(
                locationRepository.save(locationEntityMapper.modelToEntity(locationModel))
        );
    }
}
