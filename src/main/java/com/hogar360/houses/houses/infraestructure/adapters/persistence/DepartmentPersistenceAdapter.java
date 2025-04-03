package com.hogar360.houses.houses.infraestructure.adapters.persistence;

import com.hogar360.houses.houses.domain.model.DepartmentModel;
import com.hogar360.houses.houses.domain.ports.out.DepartmentPersistencePort;
import com.hogar360.houses.houses.infraestructure.mappers.DepartmentEntityMapper;
import com.hogar360.houses.houses.infraestructure.repositories.mysql.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DepartmentPersistenceAdapter implements DepartmentPersistencePort {
    private final DepartmentRepository departmentRepository;
    private final DepartmentEntityMapper departmentEntityMapper;

    @Override
    public DepartmentModel getDepartmentByName(String name) {
        return departmentEntityMapper.entityToModel(departmentRepository.findByName(name).orElse(null));
    }
}
