package com.hogar360.houses.houses.infrastructure.endpoints.rest;

import com.hogar360.houses.commons.configurations.config.ControllerConstants;
import com.hogar360.houses.commons.configurations.config.HouseControllerDocs.SaveHouseDoc;
import com.hogar360.houses.commons.configurations.config.HouseControllerDocs.SearchHousesDoc;
import com.hogar360.houses.commons.configurations.config.SwaggerExamples;
import com.hogar360.houses.houses.application.dto.request.ListHousesRequest;
import com.hogar360.houses.houses.application.dto.request.SaveHouseRequest;
import com.hogar360.houses.houses.application.dto.response.PagedHouseResponse;
import com.hogar360.houses.houses.application.dto.response.SaveHouseResponse;
import com.hogar360.houses.houses.application.services.HouseService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping(ControllerConstants.BASE_URL)
@RequiredArgsConstructor
@Tag(name = ControllerConstants.TAG, description = ControllerConstants.TAG_DESCRIPTION)
public class HouseController {

    private final HouseService houseService;

    @SaveHouseDoc
    @PostMapping(ControllerConstants.SAVE_PATH)
    public ResponseEntity<SaveHouseResponse> save(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @RequestBody SaveHouseRequest saveHouseRequest) {

        String token = authorizationHeader.replace("Bearer ", "");
        SaveHouseResponse response = houseService.save(saveHouseRequest, token);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @SearchHousesDoc
    @GetMapping(ControllerConstants.SEARCH_PATH)
    public ResponseEntity<PagedHouseResponse> search(
            @Parameter(description = SwaggerExamples.HOUSE_DEPARTMENT_DESCRIPTION, example = SwaggerExamples.HOUSE_DEPARTMENT_EXAMPLE)
            @RequestParam(required = false) String department,
            @Parameter(description = SwaggerExamples.HOUSE_CITY_DESCRIPTION, example = SwaggerExamples.HOUSE_CITY_EXAMPLE)
            @RequestParam(required = false) String city,
            @Parameter(description = SwaggerExamples.HOUSE_SECTOR_DESCRIPTION, example = SwaggerExamples.HOUSE_SECTOR_EXAMPLE)
            @RequestParam(required = false) String sector,
            @Parameter(description = SwaggerExamples.HOUSE_CATEGORY_DESCRIPTION, example = SwaggerExamples.HOUSE_CATEGORY_EXAMPLE)
            @RequestParam(required = false) String category,
            @Parameter(description = SwaggerExamples.HOUSE_BEDROOMS_DESCRIPTION, example = SwaggerExamples.HOUSE_BEDROOMS_EXAMPLE)
            @RequestParam(required = false) Integer bedrooms,
            @Parameter(description = SwaggerExamples.HOUSE_BATHROOMS_DESCRIPTION, example = SwaggerExamples.HOUSE_BATHROOMS_EXAMPLE)
            @RequestParam(required = false) Integer bathrooms,
            @Parameter(description = SwaggerExamples.HOUSE_PRICE_DESCRIPTION, example = SwaggerExamples.HOUSE_PRICE_EXAMPLE)
            @RequestParam(required = false) BigDecimal price,
            @Parameter(description = SwaggerExamples.SORT_BY_DESCRIPTION, example = SwaggerExamples.SORT_BY_EXAMPLE) // Corregido el nombre de la constante
            @RequestParam(required = false) String sortBy,
            @Parameter(description = SwaggerExamples.SORT_DIRECTION_DESCRIPTION, example = SwaggerExamples.SORT_DIRECTION_EXAMPLE)
            @RequestParam(required = false) String sortDirection,
            @Parameter(description = SwaggerExamples.PAGE_DESCRIPTION, example = SwaggerExamples.PAGE_EXAMPLE)
            @RequestParam(defaultValue = SwaggerExamples.PAGE_EXAMPLE) int page,
            @Parameter(description = SwaggerExamples.SIZE_DESCRIPTION, example = SwaggerExamples.SIZE_EXAMPLE)
            @RequestParam(defaultValue = SwaggerExamples.SIZE_EXAMPLE) int size
    ) {
        ListHousesRequest request = new ListHousesRequest(department, city, sector, category, bedrooms, bathrooms, price, sortBy, sortDirection, page, size);
        PagedHouseResponse response = houseService.listHouses(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
