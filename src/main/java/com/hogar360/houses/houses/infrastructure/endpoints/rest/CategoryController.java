package com.hogar360.houses.houses.infrastructure.endpoints.rest;

import com.hogar360.houses.houses.application.dto.request.ListCategoriesRequest;
import com.hogar360.houses.houses.application.dto.request.SaveCategoryRequest;
import com.hogar360.houses.houses.application.dto.response.PagedCategoryResponse;
import com.hogar360.houses.houses.application.dto.response.SaveCategoryResponse;
import com.hogar360.houses.houses.application.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.hogar360.houses.commons.configurations.config.SwaggerExamples.*;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
@Tag(name = "Categorías", description = "Operaciones relacionadas con las categorías")
public class CategoryController {
    private final CategoryService categoryService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/")
    @Operation(
            summary = "Guardar categoría",
            description = "Crea una nueva categoría en el sistema",
            requestBody = @RequestBody(
                    description = "Datos de la categoría a guardar",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Ejemplo de categoría",
                                    summary = "Ejemplo de creación de categoría",
                                    description = "Petición para crear una categoría tipo 'Apartamento'",
                                    value = SAVE_CATEGORY_REQUEST
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Categoría creada exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Respuesta exitosa",
                                            summary = "Respuesta de creación de categoría",
                                            description = "Respuesta con la categoría recién creada",
                                            value = CATEGORY_CREATED_MESSAGE_RESPONSE
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Solicitud inválida (por ejemplo, nombre nulo o duplicado)",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Error de validación",
                                            summary = "El nombre ya existe",
                                            description = "Se intenta crear una categoría con un nombre duplicado",
                                            value = CATEGORY_ALREADY_EXISTS_RESPONSE
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Error interno",
                                            summary = "Fallo inesperado",
                                            description = "Algo salió mal en el servidor",
                                            value = """
                                                {
                                                  "message": "Error inesperado. Intente más tarde.",
                                                  "time": "2025-04-05T21:52:50.056Z"
                                                }
                                            """
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<SaveCategoryResponse> save(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                                     @org.springframework.web.bind.annotation.RequestBody SaveCategoryRequest saveCategoryRequest) {
        String token = authorizationHeader.replace("Bearer ", "");
        SaveCategoryResponse response = categoryService.save(saveCategoryRequest, token);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/")
    @Operation(
            summary = "Listar categorías",
            description = "Lista todas las categorías de inmuebles de forma paginada",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Listado paginado de categorías",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Respuesta exitosa",
                                            summary = "Lista paginada de categorías",
                                            description = "Devuelve las categorías existentes con paginación",
                                            value = LIST_CATEGORIES_RESPONSE
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Error interno",
                                            summary = "Fallo inesperado",
                                            description = "Algo salió mal en el servidor",
                                            value = """
                        {
                          "message": "Error inesperado. Intente más tarde.",
                          "time": "2025-04-05T21:52:50.056Z"
                        }
                    """
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<PagedCategoryResponse> listCategories(

            @Parameter(description = PAGE_DESCRIPTION, example = PAGE_EXAMPLE)
            @RequestParam(defaultValue = PAGE_EXAMPLE) int page,

            @Parameter(description = SIZE_DESCRIPTION, example = SIZE_EXAMPLE)
            @RequestParam(defaultValue = SIZE_EXAMPLE) int size,

            @Parameter(description = ORDER_ASC_DESCRIPTION, example = ORDER_ASC_EXAMPLE)
            @RequestParam(defaultValue = ORDER_ASC_EXAMPLE) boolean orderAsc
    ) {
        ListCategoriesRequest request = new ListCategoriesRequest(page, size, orderAsc);
        return ResponseEntity.ok(categoryService.listCategories(request));
    }
}
