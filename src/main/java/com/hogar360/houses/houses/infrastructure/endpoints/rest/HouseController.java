package com.hogar360.houses.houses.infrastructure.endpoints.rest;

import com.hogar360.houses.commons.configurations.config.ControllerConstants;
import com.hogar360.houses.commons.configurations.config.HouseControllerDocs.SaveHouseDoc;
import com.hogar360.houses.houses.application.dto.request.SaveHouseRequest;
import com.hogar360.houses.houses.application.dto.response.SaveHouseResponse;
import com.hogar360.houses.houses.application.services.HouseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ControllerConstants.BASE_URL)
@RequiredArgsConstructor
@Tag(name = ControllerConstants.TAG, description = ControllerConstants.TAG_DESCRIPTION)
public class HouseController {
    private final HouseService houseService;

    @SaveHouseDoc
    @PostMapping(ControllerConstants.SAVE_PATH)
    public ResponseEntity<SaveHouseResponse> save(@RequestBody SaveHouseRequest saveHouseRequest) {
        SaveHouseResponse response = houseService.save(saveHouseRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
