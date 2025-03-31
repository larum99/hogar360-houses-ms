package com.hogar360.houses.houses.infraestructure.endpoints.rest;

import com.hogar360.houses.houses.application.dto.request.ListCategoriesRequest;
import com.hogar360.houses.houses.application.dto.request.SaveCategoryRequest;
import com.hogar360.houses.houses.application.dto.response.PagedCategoryResponse;
import com.hogar360.houses.houses.application.dto.response.SaveCategoryResponse;
import com.hogar360.houses.houses.application.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
@Tag(name = "Categorías", description = "Operaciones relacionadas con las categorías")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/")
    @Operation(summary = "Guardar categoría", description = "Crea una nueva categoría en el sistema")
    public ResponseEntity<SaveCategoryResponse> save(@RequestBody SaveCategoryRequest saveCategoryRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.save(saveCategoryRequest));
    }

    @GetMapping("/")
    @Operation(summary = "Listar categorías", description = "Lista todas las categorías de inmuebles de forma paginada")
    public ResponseEntity<PagedCategoryResponse> listCategories(
            @Parameter(description = "Número de página a mostrar (comienza en 0)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Cantidad de categorías por página", example = "10") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Indica si se debe ordenar ascendentemente por nombre", example = "true") @RequestParam(defaultValue = "true") boolean orderAsc) {
        ListCategoriesRequest request = new ListCategoriesRequest(page, size, orderAsc);
        return ResponseEntity.ok(categoryService.listCategories(request));
    }
}
