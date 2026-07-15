package com.hogar360.houses.houses.infrastructure.adapters.persistence;

import com.hogar360.houses.houses.domain.model.CityModel;
import com.hogar360.houses.houses.domain.ports.out.CityPersistencePort;
import com.hogar360.houses.houses.infrastructure.entities.CityEntity;
import com.hogar360.houses.houses.infrastructure.mappers.CityEntityMapper;
import com.hogar360.houses.houses.infrastructure.repositories.mysql.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CityPersistenceAdapter implements CityPersistencePort {

    private final CityRepository cityRepository;
    private final CityEntityMapper cityEntityMapper;

    @Override
    public Optional<CityModel> getCityById(Long id) {
        CityEntity cityEntity = cityRepository.findById(id).orElse(null);
        CityModel cityModel = cityEntityMapper.entityToModel(cityEntity);
        return Optional.ofNullable(cityModel);
    }

    @Override
    public List<CityModel> findCitiesByDepartmentId(Long departmentId) {
        List<CityEntity> cityEntities = cityRepository.findByDepartmentId(departmentId);
        List<CityModel> cityModels = new ArrayList<>();

        for (CityEntity entity : cityEntities) {
            cityModels.add(cityEntityMapper.entityToModel(entity));
        }

        return cityModels;
    }
}
