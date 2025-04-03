package com.hogar360.houses.houses.infraestructure.repositories.mysql;

import com.hogar360.houses.houses.infraestructure.entities.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, Long> {
}
