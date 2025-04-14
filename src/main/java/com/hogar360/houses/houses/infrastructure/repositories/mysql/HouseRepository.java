package com.hogar360.houses.houses.infrastructure.repositories.mysql;

import com.hogar360.houses.houses.domain.utils.PublicationStatus;
import com.hogar360.houses.houses.infrastructure.entities.HouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HouseRepository extends JpaRepository<HouseEntity, Long> {
    List<HouseEntity> findByActivePublicationDateLessThanEqualAndStatusNot(LocalDate date, PublicationStatus status);
}
