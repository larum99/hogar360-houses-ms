package com.hogar360.houses.houses.application.services.impl;

import com.hogar360.houses.commons.configurations.utils.Constants;
import com.hogar360.houses.houses.application.dto.request.SaveHouseRequest;
import com.hogar360.houses.houses.application.dto.response.SaveHouseResponse;
import com.hogar360.houses.houses.application.mappers.HouseDtoMapper;
import com.hogar360.houses.houses.application.services.HouseService;
import com.hogar360.houses.houses.domain.model.HouseModel;
import com.hogar360.houses.houses.domain.ports.in.HouseServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final HouseServicePort houseServicePort;
    private final HouseDtoMapper houseDtoMapper;

    @Override
    public SaveHouseResponse save(SaveHouseRequest request) {

        HouseModel houseModel = houseDtoMapper.requestToModel(request);

        houseServicePort.save(houseModel);

        return new SaveHouseResponse(Constants.SAVE_HOUSE_RESPONSE_MESSAGE, LocalDateTime.now());
    }
}
