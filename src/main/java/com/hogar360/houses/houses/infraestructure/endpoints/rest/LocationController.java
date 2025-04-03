package com.hogar360.houses.houses.infraestructure.endpoints.rest;

import com.hogar360.houses.houses.application.dto.request.SaveLocationRequest;
import com.hogar360.houses.houses.application.dto.response.SaveLocationResponse;
import com.hogar360.houses.houses.application.services.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/location")
@RequiredArgsConstructor
@Tag(name = "Ubicaciones", description = "Operaciones relacionadas con las ubicaciones")
public class LocationController {
     private final LocationService locationService;

    @PostMapping("/")
    @Operation(summary = "Guardar ubicación", description = "Crea una nueva ubicación en el sistema")
    public ResponseEntity<SaveLocationResponse> save(@RequestBody SaveLocationRequest saveLocationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(locationService.save(saveLocationRequest));
    }
}
