package com.hogar360.houses.houses.application.services;

import com.hogar360.houses.houses.application.dto.request.ListHousesRequest;
import com.hogar360.houses.houses.application.dto.request.SaveHouseRequest;
import com.hogar360.houses.houses.application.dto.response.PagedHouseResponse;
import com.hogar360.houses.houses.application.dto.response.SaveHouseResponse;

public interface HouseService {
    SaveHouseResponse save(SaveHouseRequest request, String token);
    PagedHouseResponse listHouses(ListHousesRequest request, String token);
    Long getOwnerIdByHouseId(Long houseId);
}
