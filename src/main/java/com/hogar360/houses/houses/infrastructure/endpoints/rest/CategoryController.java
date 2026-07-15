package com.hogar360.houses.houses.infrastructure.endpoints.rest;

import com.hogar360.houses.commons.configurations.config.CategoryControllerDocs.*;
import com.hogar360.houses.commons.configurations.config.ControllerConstants;
import com.hogar360.houses.houses.application.dto.request.ListCategoriesRequest;
import com.hogar360.houses.houses.application.dto.request.SaveCategoryRequest;
import com.hogar360.houses.houses.application.dto.response.PagedCategoryResponse;
import com.hogar360.houses.houses.application.dto.response.SaveCategoryResponse;
import com.hogar360.houses.houses.application.services.CategoryService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.hogar360.houses.commons.configurations.config.SwaggerExamples.*;

@RestController
@RequestMapping(ControllerConstants.BASE_URL_CATEGORIES)
@RequiredArgsConstructor
@Tag(name = ControllerConstants.TAG_CATEGORIES, description = ControllerConstants.TAG_DESCRIPTION_CATEGORIES)
public class CategoryController {
    private final CategoryService categoryService;

    @SaveCategoryDoc
    @PreAuthorize(ControllerConstants.ROLE_ADMIN)
    @PostMapping(ControllerConstants.PATH)
    public ResponseEntity<SaveCategoryResponse> save(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                                     @RequestBody SaveCategoryRequest saveCategoryRequest) {
        String token = authorizationHeader.replace(ControllerConstants.BEARER_PREFIX, "");
        SaveCategoryResponse response = categoryService.save(saveCategoryRequest, token);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @ListCategoriesDoc
    @GetMapping(ControllerConstants.PATH)
    public ResponseEntity<PagedCategoryResponse> listCategories(

            @Parameter(description = PAGE_DESCRIPTION, example = PAGE_EXAMPLE)
            @RequestParam(defaultValue = PAGE_EXAMPLE) int page,

            @Parameter(description = SIZE_DESCRIPTION, example = SIZE_EXAMPLE)
            @RequestParam(defaultValue = SIZE_EXAMPLE) int size,

            @Parameter(description = ORDER_ASC_DESCRIPTION, example = ORDER_ASC_EXAMPLE)
            @RequestParam(defaultValue = ORDER_ASC_EXAMPLE) boolean orderAsc
    ) {
        ListCategoriesRequest request = new ListCategoriesRequest(page, size, orderAsc);
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.listCategories(request));
    }
}
