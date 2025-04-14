package com.hogar360.houses.houses.infrastructure.adapters.persistence;

import com.hogar360.houses.houses.domain.model.LocationModel;
import com.hogar360.houses.houses.domain.utils.PageResult;
import com.hogar360.houses.houses.domain.ports.out.LocationPersistencePort;
import com.hogar360.houses.houses.infrastructure.entities.LocationEntity;
import com.hogar360.houses.houses.infrastructure.mappers.LocationEntityMapper;
import com.hogar360.houses.houses.infrastructure.repositories.mysql.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.hogar360.houses.commons.configurations.utils.SortUtils.createSort;

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
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<LocationEntity> locationEntityPage =
                locationRepository.searchByCityOrDepartment(searchTerm, pageable);

        return convertToPageModel(locationEntityPage);
    }

    @Override
    public Optional<LocationModel> findById(Long id) {
        LocationEntity entity = locationRepository.findById(id).orElse(null);
        LocationModel model = locationEntityMapper.entityToModel(entity);
        return Optional.ofNullable(model);
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
