package com.hogar360.houses.houses.application.services.impl;

import com.hogar360.houses.commons.configurations.utils.Constants;
import com.hogar360.houses.houses.application.dto.request.ListHousesRequest;
import com.hogar360.houses.houses.application.dto.request.SaveHouseRequest;
import com.hogar360.houses.houses.application.dto.response.HouseResponse;
import com.hogar360.houses.houses.application.dto.response.PagedHouseResponse;
import com.hogar360.houses.houses.application.dto.response.SaveHouseResponse;
import com.hogar360.houses.houses.application.mappers.HouseDtoMapper;
import com.hogar360.houses.houses.application.mappers.HouseSearchCriteriaMapper;
import com.hogar360.houses.houses.application.services.HouseService;
import com.hogar360.houses.houses.domain.model.HouseModel;
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

    @Override
    public SaveHouseResponse save(SaveHouseRequest request, String token) {
        String role = roleValidatorPort.extractRole(token);
        Long userId = roleValidatorPort.extractUserId(token);

        houseServicePort.save(houseDtoMapper.requestToModel(request), role, userId);

        return new SaveHouseResponse(Constants.SAVE_HOUSE_RESPONSE_MESSAGE, LocalDateTime.now());
    }

    @Override
    public PagedHouseResponse listHouses(ListHousesRequest request, String token) {
        String role = roleValidatorPort.extractRole(token);
        var criteria = houseSearchCriteriaMapper.requestToCriteria(request);
        criteria.setPublisherId(request.publisherId());
        PageResult<HouseModel> pageResult = houseServicePort.searchHouses(criteria, role);
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
}
