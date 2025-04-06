package com.hogar360.houses.houses.infraestructure.adapters.persistence;

import com.hogar360.houses.houses.domain.model.LocationModel;
import com.hogar360.houses.houses.domain.utils.PageResult;
import com.hogar360.houses.houses.domain.ports.out.LocationPersistencePort;
import com.hogar360.houses.houses.infraestructure.entities.LocationEntity;
import com.hogar360.houses.houses.infraestructure.mappers.LocationEntityMapper;
import com.hogar360.houses.houses.infraestructure.repositories.mysql.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LocationPersistenceAdapter implements LocationPersistencePort {
    private final LocationRepository locationRepository;
    private final LocationEntityMapper locationEntityMapper;

    @Override
    public LocationModel save(LocationModel locationModel) {
        return locationEntityMapper.entityToModel(
                locationRepository.save(locationEntityMapper.modelToEntity(locationModel))
        );
    }

    @Override
    public PageResult<LocationModel> searchLocations(String searchTerm, int page, int size, String sortBy, String sortDirection) {
        Sort sort = createSort(sortBy, sortDirection);
        Pageable pageable = createPageable(page, size, sort);

        Page<LocationEntity> locationEntityPage =
                locationRepository.searchByCityOrDepartment(searchTerm, pageable);

        return convertToPageModel(locationEntityPage);
    }

    private Sort createSort(String sortBy, String sortDirection) {
        Sort sort = Sort.by(sortBy);
        if (sortDirection != null && sortDirection.equalsIgnoreCase("desc")) {
            sort = sort.descending();
        }
        return sort;
    }

    private Pageable createPageable(int page, int size, Sort sort) {
        return PageRequest.of(page, size, sort);
    }

    private PageResult<LocationModel> convertToPageModel(Page<LocationEntity> entityPage) {
        List<LocationModel> locationModels = locationEntityMapper.entityToModelList(entityPage.getContent());

        return new PageResult<>(
                locationModels,
                entityPage.getTotalElements(),
                entityPage.getTotalPages(),
                entityPage.getNumber(),
                entityPage.getSize(),
                entityPage.isFirst(),
                entityPage.isLast()
        );
    }
}
