package com.hogar360.houses.houses.application.services.impl;

import com.hogar360.houses.commons.configurations.utils.Constants;
import com.hogar360.houses.houses.application.dto.request.ListHousesRequest;
import com.hogar360.houses.houses.application.dto.request.SaveHouseRequest;
import com.hogar360.houses.houses.application.dto.response.*;
import com.hogar360.houses.houses.application.mappers.HouseDtoMapper;
import com.hogar360.houses.houses.application.mappers.HouseSearchCriteriaMapper;
import com.hogar360.houses.houses.application.mappers.LocationDtoMapper;
import com.hogar360.houses.houses.application.services.HouseService;
import com.hogar360.houses.houses.domain.model.HouseModel;
import com.hogar360.houses.houses.domain.model.LocationModel;
import com.hogar360.houses.houses.domain.ports.in.HouseServicePort;
import com.hogar360.houses.houses.domain.ports.in.RoleValidatorPort;
import com.hogar360.houses.houses.domain.utils.PageResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final HouseServicePort houseServicePort;
    private final HouseDtoMapper houseDtoMapper;
    private final HouseSearchCriteriaMapper houseSearchCriteriaMapper;
    private final RoleValidatorPort roleValidatorPort;
    private final LocationDtoMapper locationDtoMapper;

    @Override
    public SaveHouseResponse save(SaveHouseRequest request, String token) {
        String role = roleValidatorPort.extractRole(token);
        Long userId = roleValidatorPort.extractUserId(token);

        houseServicePort.save(houseDtoMapper.requestToModel(request), role, userId);

        return new SaveHouseResponse(Constants.SAVE_HOUSE_RESPONSE_MESSAGE, LocalDateTime.now());
    }

    @Override
    public PagedHouseResponse listHouses(ListHousesRequest request, String token) {
        String role;
        Long userId = null;
        if (token == null || token.isBlank()) {
            role = "ANONYMOUS";
        } else {
            role = roleValidatorPort.extractRole(token);
            userId = roleValidatorPort.extractUserId(token);
        }
        var criteria = houseSearchCriteriaMapper.requestToCriteria(request);
        criteria.setPublisherId(request.publisherId());
        PageResult<HouseModel> pageResult = houseServicePort.searchHouses(criteria, role, userId);
        List<HouseResponse> responses = houseDtoMapper.modelToResponseList(pageResult.getContent());

        return new PagedHouseResponse(
                responses,
                pageResult.getTotalElements(),
                pageResult.getTotalPages(),
                pageResult.getCurrentPage(),
                pageResult.getPageSize(),
                pageResult.isFirst(),
                pageResult.isLast()
        );
    }

    @Override
    public Long getOwnerIdByHouseId(Long houseId) {
        return houseServicePort.findPublisherIdById(houseId);
    }

    @Override
    public List<HouseResponse> listHousesByPublisherId(Long publisherId) {
        List<HouseModel> houses = houseServicePort.findAllByPublisherId(publisherId);
        return houseDtoMapper.modelToResponseList(houses);
    }

    @Override
    public List<Long> getHouseIdsByLocation(Long cityId, String sector) {
        return houseServicePort.findIdsByCityIdAndSector(cityId, sector);
    }

    @Override
    public HouseSimpleResponse getHouseById(Long houseId) {
        HouseModel houseModel = houseServicePort.findById(houseId);
        LocationModel locationModel = houseModel.getLocation();
        LocationResponse locationResponse = null;
        if (locationModel != null) {
            locationResponse = locationDtoMapper.modelToResponse(locationModel);
        }
        return new HouseSimpleResponse(houseModel.getId(), houseModel.getName(), locationResponse);
    }
}
