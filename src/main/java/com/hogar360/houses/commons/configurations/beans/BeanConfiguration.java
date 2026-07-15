package com.hogar360.houses.commons.configurations.beans;

import com.hogar360.houses.houses.domain.ports.in.*;
import com.hogar360.houses.houses.domain.ports.out.*;
import com.hogar360.houses.houses.domain.usecases.*;
import com.hogar360.houses.houses.infrastructure.adapters.persistence.*;
import com.hogar360.houses.houses.infrastructure.mappers.*;
import com.hogar360.houses.houses.infrastructure.repositories.mysql.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final CategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;
    private final CityRepository cityRepository;
    private final CityEntityMapper cityEntityMapper;
    private final LocationRepository locationRepository;
    private final LocationEntityMapper locationEntityMapper;
    private final HouseRepository houseRepository;
    private final HouseEntityMapper houseEntityMapper;
    private final DepartmentRepository departmentRepository;
    private final DepartmentEntityMapper departmentEntityMapper;

    @Bean
    public CategoryServicePort categoryServicePort() {
        return new CategoryUseCase(categoryPersistencePort());
    }

    @Bean
    public CategoryPersistencePort categoryPersistencePort() {
        return new CategoryPersistenceAdapter(categoryRepository, categoryEntityMapper);
    }
    @Bean
    public LocationServicePort locationServicePort() {
        return new LocationUseCase(cityPersistencePort(), locationPersistencePort());
    }

    @Bean
    public LocationPersistencePort locationPersistencePort() {
        return new LocationPersistenceAdapter(locationRepository, locationEntityMapper);
    }

    @Bean
    public CityPersistencePort cityPersistencePort() {
        return new CityPersistenceAdapter(cityRepository, cityEntityMapper);
    }


    @Bean
    public HouseServicePort houseServicePort() {
        return new HouseUseCase(housePersistencePort(), categoryPersistencePort(),
                locationPersistencePort());
    }

    @Bean
    public HousePersistencePort housePersistencePort() {
        return new HousePersistenceAdapter(houseRepository, houseEntityMapper);
    }

    @Bean
    public CityServicePort cityServicePort() {
        return new CityUseCase(cityPersistencePort());
    }

    @Bean
    public DepartmentServicePort departmentServicePort() {
        return new DepartmentUseCase(departmentPersistencePort());
    }

    @Bean
    public DepartmentPersistencePort departmentPersistencePort() {
        return new DepartmentPersistenceAdapter(departmentRepository, departmentEntityMapper);
    }

}
