package com.hogar360.houses.houses.application.services;

import com.hogar360.houses.houses.application.dto.request.SaveLocationRequest;
import com.hogar360.houses.houses.application.dto.response.SaveLocationResponse;

public interface LocationService {
    SaveLocationResponse save(SaveLocationRequest request);
}
