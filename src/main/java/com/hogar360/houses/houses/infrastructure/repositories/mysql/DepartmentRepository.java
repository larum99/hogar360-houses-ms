package com.hogar360.houses.houses.infrastructure.repositories.mysql;

import com.hogar360.houses.houses.infrastructure.entities.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {
}
