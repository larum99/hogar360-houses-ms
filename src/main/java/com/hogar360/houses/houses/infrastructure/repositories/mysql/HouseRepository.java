package com.hogar360.houses.houses.infrastructure.repositories.mysql;

import com.hogar360.houses.houses.domain.utils.PublicationStatus;
import com.hogar360.houses.houses.infrastructure.entities.HouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HouseRepository extends JpaRepository<HouseEntity, Long>, JpaSpecificationExecutor<HouseEntity> {
    List<HouseEntity> findByActivePublicationDateLessThanEqualAndStatusNot(LocalDate date, PublicationStatus status);
    @Query("SELECT h.publisherId FROM HouseEntity h WHERE h.id = :houseId")
    Long findPublisherIdById(@Param("houseId") Long houseId);
    boolean existsByNameAndLocationId(String name, Long locationId);
    List<HouseEntity> findAllByPublisherId(Long publisherId);
    @Query("SELECT h.id FROM HouseEntity h WHERE h.location.city.id = :cityId AND h.location.sector = :sector")
    List<Long> findIdsByLocation_CityIdAndLocation_Sector(@Param("cityId") Long cityId, @Param("sector") String sector);
}
