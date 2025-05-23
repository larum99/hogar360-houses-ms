package com.hogar360.houses.houses.infrastructure.repositories.mysql;

import com.hogar360.houses.houses.infrastructure.entities.LocationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, Long> {

    @Query("""
        SELECT l FROM LocationEntity l
        WHERE LOWER(l.city.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
           OR LOWER(l.city.department.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
               OR LOWER(l.sector) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
    """)
    Page<LocationEntity> searchByCityOrDepartment(@Param("searchTerm") String searchTerm, Pageable pageable);

    Optional<LocationEntity> findBySectorAndCityId(String sector, Long cityId);

    List<LocationEntity> findByCityId(Long cityId);
}
