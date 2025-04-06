package com.hogar360.houses.houses.infraestructure.adapters.persistence;

import com.hogar360.houses.houses.domain.model.CityModel;
import com.hogar360.houses.houses.domain.model.DepartmentModel;
import com.hogar360.houses.houses.domain.ports.out.CityPersistencePort;
import com.hogar360.houses.houses.infraestructure.entities.CityEntity;
import com.hogar360.houses.houses.infraestructure.entities.DepartmentEntity;
import com.hogar360.houses.houses.infraestructure.mappers.CityEntityMapper;
import com.hogar360.houses.houses.infraestructure.repositories.mysql.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CityPersistenceAdapter implements CityPersistencePort {
    private final CityRepository cityRepository;
    private final CityEntityMapper cityEntityMapper;

    @Override
    public CityModel getCityById(Long id) {
        CityEntity cityEntity = cityRepository.findById(id).orElse(null);
        return cityEntityMapper.entityToModel(cityEntity);
    }
}
