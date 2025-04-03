package com.hogar360.houses.houses.infraestructure.adapters.persistence;

import com.hogar360.houses.houses.domain.model.LocationModel;
import com.hogar360.houses.houses.domain.model.PageModel;
import com.hogar360.houses.houses.domain.ports.out.LocationPersistencePort;
import com.hogar360.houses.houses.infraestructure.mappers.LocationEntityMapper;
import com.hogar360.houses.houses.infraestructure.repositories.mysql.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

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
    public LocationModel getById(Long id) {
        return locationRepository.findById(id)
                .map(locationEntityMapper::entityToModel)
                .orElse(null);
    }

    @Override
    public PageModel<LocationModel> searchLocations(String searchTerm, int page, int size, String sortBy, String sortDirection) {
        Sort sort = createSort(sortBy, sortDirection);
        Pageable pageable = createPageable(page, size, sort);

        org.springframework.data.domain.Page<com.hogar360.houses.houses.infraestructure.entities.LocationEntity> locationEntityPage =
                locationRepository.findByCity_NameContainingIgnoreCaseOrCity_Department_NameContainingIgnoreCase(searchTerm, searchTerm, pageable);

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

    private PageModel<LocationModel> convertToPageModel(org.springframework.data.domain.Page<com.hogar360.houses.houses.infraestructure.entities.LocationEntity> entityPage) {
        return new PageModel<>(
                entityPage.getContent().stream()
                        .map(locationEntityMapper::entityToModel)
                        .collect(Collectors.toList()),
                entityPage.getTotalElements(),
                entityPage.getTotalPages(),
                entityPage.getNumber(),
                entityPage.getSize(),
                entityPage.isFirst(),
                entityPage.isLast()
        );
    }
}
