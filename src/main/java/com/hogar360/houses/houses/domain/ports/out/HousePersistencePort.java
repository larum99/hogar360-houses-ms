package com.hogar360.houses.houses.domain.ports.out;

import com.hogar360.houses.houses.domain.criteria.HouseSearchCriteria;
import com.hogar360.houses.houses.domain.model.HouseModel;
import com.hogar360.houses.houses.domain.utils.PageResult;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HousePersistencePort {
    void save(HouseModel houseModel);
    List<HouseModel> findAllPendingToPublish(LocalDate today);
    PageResult<HouseModel> search(HouseSearchCriteria criteria);

    Long findPublisherIdById(Long houseId);
    boolean existsByNameAndLocationId(String name, Long locationId);
    List<HouseModel> findAllByPublisherId(Long publisherId);
    List<Long> findIdsByCityIdAndSector(Long cityId, String sector);
    HouseModel findById(Long houseId);
}
