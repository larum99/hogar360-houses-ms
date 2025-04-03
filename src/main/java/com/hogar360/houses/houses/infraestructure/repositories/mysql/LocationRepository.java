package com.hogar360.houses.houses.infraestructure.repositories.mysql;

import com.hogar360.houses.houses.infraestructure.entities.LocationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, Long> {
    Page<LocationEntity> findByCity_NameContainingIgnoreCaseOrCity_Department_NameContainingIgnoreCase(String cityName, String departmentName, Pageable pageable);
}
