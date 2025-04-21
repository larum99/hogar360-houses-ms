package com.hogar360.houses.commons.configurations.beans;

import com.hogar360.houses.houses.domain.ports.in.CategoryServicePort;
import com.hogar360.houses.houses.domain.ports.in.HouseServicePort;
import com.hogar360.houses.houses.domain.ports.in.LocationServicePort;
import com.hogar360.houses.houses.domain.ports.out.CategoryPersistencePort;
import com.hogar360.houses.houses.domain.ports.out.CityPersistencePort;
import com.hogar360.houses.houses.domain.ports.out.HousePersistencePort;
import com.hogar360.houses.houses.domain.ports.out.LocationPersistencePort;
import com.hogar360.houses.houses.domain.usecases.CategoryUseCase;
import com.hogar360.houses.houses.domain.usecases.HouseUseCase;
import com.hogar360.houses.houses.domain.usecases.LocationUseCase;
import com.hogar360.houses.houses.infrastructure.adapters.persistence.CategoryPersistenceAdapter;
import com.hogar360.houses.houses.infrastructure.adapters.persistence.CityPersistenceAdapter;
import com.hogar360.houses.houses.infrastructure.adapters.persistence.HousePersistenceAdapter;
import com.hogar360.houses.houses.infrastructure.adapters.persistence.LocationPersistenceAdapter;
import com.hogar360.houses.houses.infrastructure.mappers.CategoryEntityMapper;
import com.hogar360.houses.houses.infrastructure.mappers.CityEntityMapper;
import com.hogar360.houses.houses.infrastructure.mappers.HouseEntityMapper;
import com.hogar360.houses.houses.infrastructure.mappers.LocationEntityMapper;
import com.hogar360.houses.houses.infrastructure.repositories.mysql.CategoryRepository;
import com.hogar360.houses.houses.infrastructure.repositories.mysql.CityRepository;
import com.hogar360.houses.houses.infrastructure.repositories.mysql.HouseRepository;
import com.hogar360.houses.houses.infrastructure.repositories.mysql.LocationRepository;
import com.hogar360.houses.houses.infrastructure.security.JwtRoleValidator;
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
    private final JwtRoleValidator jwtRoleValidator;

    @Bean
    public CategoryServicePort categoryServicePort() {
        return new CategoryUseCase(categoryPersistencePort(), jwtRoleValidator);
    }

    @Bean
    public CategoryPersistencePort categoryPersistencePort() {
        return new CategoryPersistenceAdapter(categoryRepository, categoryEntityMapper);
    }
    @Bean
    public LocationServicePort locationServicePort() {
        return new LocationUseCase(cityPersistencePort(), locationPersistencePort(), jwtRoleValidator);
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
                locationPersistencePort(), jwtRoleValidator);
    }

    @Bean
    public HousePersistencePort housePersistencePort() {
        return new HousePersistenceAdapter(houseRepository, houseEntityMapper);
    }

}
