package com.hogar360.houses.houses.infrastructure.repositories.mysql;

import com.hogar360.houses.houses.infrastructure.entities.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, Long> {
}
