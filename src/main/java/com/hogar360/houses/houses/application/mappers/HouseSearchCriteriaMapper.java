package com.hogar360.houses.houses.application.mappers;

import com.hogar360.houses.houses.application.dto.request.ListHousesRequest;
import com.hogar360.houses.houses.domain.criteria.HouseSearchCriteria;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HouseSearchCriteriaMapper {

    HouseSearchCriteria requestToCriteria(ListHousesRequest listHousesRequest);

}
