package com.hogar360.houses.houses.infraestructure.repositories.mysql;

import com.hogar360.houses.houses.infraestructure.entities.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {
}
