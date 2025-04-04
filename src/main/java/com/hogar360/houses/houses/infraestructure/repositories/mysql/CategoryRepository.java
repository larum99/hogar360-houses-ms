package com.hogar360.houses.houses.infraestructure.repositories.mysql;

import com.hogar360.houses.houses.domain.utils.PageResult;
import com.hogar360.houses.houses.infraestructure.entities.CategoryEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByName(String name);


}


