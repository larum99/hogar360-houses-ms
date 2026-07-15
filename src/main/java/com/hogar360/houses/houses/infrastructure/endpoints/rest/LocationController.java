package com.hogar360.houses.houses.infrastructure.endpoints.rest;

import com.hogar360.houses.commons.configurations.config.ControllerConstants;
import com.hogar360.houses.commons.configurations.config.LocationControllerDocs.*;
import com.hogar360.houses.houses.application.dto.request.SaveLocationRequest;
import com.hogar360.houses.houses.application.dto.response.LocationSimpleResponse;
import com.hogar360.houses.houses.application.dto.response.PagedLocationResponse;
import com.hogar360.houses.houses.application.dto.response.SaveLocationResponse;
import com.hogar360.houses.houses.application.services.LocationService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.hogar360.houses.commons.configurations.config.SwaggerExamples.*;

@RestController
@RequestMapping(ControllerConstants.BASE_URL_LOCATIONS)
@RequiredArgsConstructor
@Tag(name = ControllerConstants.TAG_LOCATIONS, description = ControllerConstants.TAG_DESCRIPTION_LOCATIONS)
public class LocationController {
     private final LocationService locationService;

     @SaveLocationDoc
     @PostMapping(ControllerConstants.PATH)
     @PreAuthorize(ControllerConstants.ROLE_ADMIN)
     public ResponseEntity<SaveLocationResponse> save(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @RequestBody SaveLocationRequest saveLocationRequest) {

        String token = authorizationHeader.replace(ControllerConstants.BEARER_PREFIX, "");
        SaveLocationResponse response = locationService.save(saveLocationRequest, token);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
     }

    @GetMapping(ControllerConstants.SEARCH_PATH)
    public ResponseEntity<PagedLocationResponse> searchLocations(
            @Parameter(description = SEARCH_TERM_DESCRIPTION, example = SEARCH_TERM_EXAMPLE)
            @RequestParam String searchTerm,

            @Parameter(description = PAGE_DESCRIPTION, example = PAGE_EXAMPLE)
            @RequestParam(defaultValue = PAGE_EXAMPLE) int page,

            @Parameter(description = SIZE_DESCRIPTION, example = SIZE_EXAMPLE)
            @RequestParam(defaultValue = SIZE_DESCRIPTION) int size,

            @Parameter(description = SORT_BY_DESCRIPTION, example = SORT_BY_EXAMPLE)
            @RequestParam(defaultValue = SORT_BY_EXAMPLE) String sortBy,

            @Parameter(description = SORT_DIRECTION_DESCRIPTION, example = SORT_DIRECTION_EXAMPLE)
            @RequestParam(defaultValue = SORT_DIRECTION_EXAMPLE) String sortDirection
    ) {
        PagedLocationResponse response = locationService.searchLocations(searchTerm, page, size, sortBy, sortDirection);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetLocationsByCityIdDoc
    @GetMapping(ControllerConstants.GET_BY_CITY)
    public ResponseEntity<List<LocationSimpleResponse>> getLocationsByCityId(@PathVariable Long cityId) {
        return ResponseEntity.status(HttpStatus.OK).body(locationService.findByCityId(cityId));
    }
}
