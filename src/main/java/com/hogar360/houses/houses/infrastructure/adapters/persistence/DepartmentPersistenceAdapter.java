package com.hogar360.houses.houses.infrastructure.adapters.persistence;

import com.hogar360.houses.houses.domain.model.DepartmentModel;
import com.hogar360.houses.houses.domain.ports.out.DepartmentPersistencePort;
import com.hogar360.houses.houses.infrastructure.entities.DepartmentEntity;
import com.hogar360.houses.houses.infrastructure.mappers.DepartmentEntityMapper;
import com.hogar360.houses.houses.infrastructure.repositories.mysql.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DepartmentPersistenceAdapter implements DepartmentPersistencePort {

    private final DepartmentRepository departmentRepository;
    private final DepartmentEntityMapper departmentEntityMapper;

    @Override
    public List<DepartmentModel> findAllDepartments() {
        List<DepartmentEntity> departmentEntities = departmentRepository.findAll();
        List<DepartmentModel> departmentModels = new ArrayList<>();

        for (DepartmentEntity entity : departmentEntities) {
            departmentModels.add(departmentEntityMapper.entityToModel(entity));
        }

        return departmentModels;
    }
}
