package com.hogar360.houses.commons.configurations.beans;

import com.hogar360.houses.houses.domain.ports.in.CategoryServicePort;
import com.hogar360.houses.houses.domain.ports.in.LocationServicePort;
import com.hogar360.houses.houses.domain.ports.out.CategoryPersistencePort;
import com.hogar360.houses.houses.domain.ports.out.CityPersistencePort;
import com.hogar360.houses.houses.domain.ports.out.DepartmentPersistencePort;
import com.hogar360.houses.houses.domain.ports.out.LocationPersistencePort;
import com.hogar360.houses.houses.domain.usecases.CategoryUseCase;
import com.hogar360.houses.houses.domain.usecases.LocationUseCase;
import com.hogar360.houses.houses.infraestructure.adapters.persistence.CategoryPersistenceAdapter;
import com.hogar360.houses.houses.infraestructure.adapters.persistence.CityPersistenceAdapter;
import com.hogar360.houses.houses.infraestructure.adapters.persistence.DepartmentPersistenceAdapter;
import com.hogar360.houses.houses.infraestructure.adapters.persistence.LocationPersistenceAdapter;
import com.hogar360.houses.houses.infraestructure.mappers.CategoryEntityMapper;
import com.hogar360.houses.houses.infraestructure.mappers.CityEntityMapper;
import com.hogar360.houses.houses.infraestructure.mappers.DepartmentEntityMapper;
import com.hogar360.houses.houses.infraestructure.mappers.LocationEntityMapper;
import com.hogar360.houses.houses.infraestructure.repositories.mysql.CategoryRepository;
import com.hogar360.houses.houses.infraestructure.repositories.mysql.CityRepository;
import com.hogar360.houses.houses.infraestructure.repositories.mysql.DepartmentRepository;
import com.hogar360.houses.houses.infraestructure.repositories.mysql.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final CategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;
    private final DepartmentRepository departmentRepository;
    private final DepartmentEntityMapper departmentEntityMapper;
    private final CityRepository cityRepository;
    private final CityEntityMapper cityEntityMapper;
    private final LocationRepository locationRepository;
    private final LocationEntityMapper locationEntityMapper;

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
    public DepartmentPersistencePort departmentPersistencePort() {
        return new DepartmentPersistenceAdapter(departmentRepository, departmentEntityMapper);
    }

    @Bean
    public CityPersistencePort cityPersistencePort() {
        return new CityPersistenceAdapter(cityRepository, cityEntityMapper);
    }
}
