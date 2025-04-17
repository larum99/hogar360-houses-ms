package com.hogar360.houses.houses.infrastructure.adapters.persistence;

import com.hogar360.houses.houses.domain.criteria.HouseSearchCriteria;
import com.hogar360.houses.houses.domain.model.HouseModel;
import com.hogar360.houses.houses.domain.ports.out.HousePersistencePort;
import com.hogar360.houses.houses.domain.utils.PageResult;
import com.hogar360.houses.houses.domain.utils.PublicationStatus;
import com.hogar360.houses.houses.infrastructure.entities.HouseEntity;
import com.hogar360.houses.houses.infrastructure.mappers.HouseEntityMapper;
import com.hogar360.houses.houses.infrastructure.repositories.mysql.HouseRepository;
import com.hogar360.houses.houses.infrastructure.specifications.HouseSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.hogar360.houses.commons.configurations.utils.SortUtils.createSort;

@Service
@RequiredArgsConstructor
public class HousePersistenceAdapter implements HousePersistencePort {

    private final HouseRepository houseRepository;
    private final HouseEntityMapper houseEntityMapper;

    @Override
    public void save(HouseModel houseModel) {
        houseRepository.save(houseEntityMapper.modelToEntity(houseModel));
    }

    @Override
    public List<HouseModel> findAllPendingToPublish(LocalDate today) {
        return houseEntityMapper.entityListToModelList(
                houseRepository.findByActivePublicationDateLessThanEqualAndStatusNot(today, PublicationStatus.PUBLISHED)
        );
    }

    @Override
    public PageResult<HouseModel> search(HouseSearchCriteria criteria) {
        Sort sort = createSort(criteria.getSortBy(), "asc".equalsIgnoreCase(criteria.getSortDirection()));
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize(), sort);

        var spec = HouseSpecification.hasSector(criteria.getSector())
                .and(HouseSpecification.hasCity(criteria.getCity()))
                .and(HouseSpecification.hasDepartment(criteria.getDepartment()))
                .and(HouseSpecification.hasCategory(criteria.getCategory()))
                .and(HouseSpecification.hasBedrooms(criteria.getBedrooms()))
                .and(HouseSpecification.hasBathrooms(criteria.getBathrooms()))
                .and(HouseSpecification.hasPrice(
                        criteria.getPrice(),
                        criteria.getMinPrice(),
                        criteria.getMaxPrice()
                ))
                .and(HouseSpecification.isPublished());

        Page<HouseEntity> page = houseRepository.findAll(spec, pageable);

        List<HouseModel> houseModels = houseEntityMapper.entityListToModelList(page.getContent());

        return new PageResult<>(
                houseModels,
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getSize(),
                page.isFirst(),
                page.isLast()
        );
    }
}
