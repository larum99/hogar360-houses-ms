package com.hogar360.houses.houses.infrastructure.adapters.persistence;

import com.hogar360.houses.houses.domain.model.HouseModel;
import com.hogar360.houses.houses.domain.ports.out.HousePersistencePort;
import com.hogar360.houses.houses.domain.utils.PublicationStatus;
import com.hogar360.houses.houses.infrastructure.mappers.HouseEntityMapper;
import com.hogar360.houses.houses.infrastructure.repositories.mysql.HouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
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
}
