package com.hogar360.houses.houses.domain.ports.out;

import com.hogar360.houses.houses.domain.model.HouseModel;

import java.time.LocalDate;
import java.util.List;

public interface HousePersistencePort {
    void save(HouseModel houseModel);
    List<HouseModel> findAllPendingToPublish(LocalDate today);

}
