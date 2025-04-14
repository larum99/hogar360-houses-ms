package com.hogar360.houses.houses.infrastructure.endpoints.rest;

import com.hogar360.houses.commons.configurations.config.SwaggerExamples;
import com.hogar360.houses.houses.application.dto.request.SaveLocationRequest;
import com.hogar360.houses.houses.application.dto.response.PagedLocationResponse;
import com.hogar360.houses.houses.application.dto.response.SaveLocationResponse;
import com.hogar360.houses.houses.application.services.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.hogar360.houses.commons.configurations.config.SwaggerExamples.*;

@RestController
@RequestMapping("/api/v1/location")
@RequiredArgsConstructor
@Tag(name = "Ubicaciones", description = "Operaciones relacionadas con las ubicaciones")
public class LocationController {
     private final LocationService locationService;

    @PostMapping("/")
    @Operation(
            summary = "Guardar ubicación",
            description = "Crea una nueva ubicación en el sistema",
            requestBody = @RequestBody(
                    description = "Datos de la ubicación a guardar",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Ejemplo de ubicación",
                                    summary = "Ejemplo de creación de ubicación",
                                    description = "Petición para crear una ubicación en Bogotá, Colombia",
                                    value = SAVE_LOCATION_REQUEST
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Ubicación creada exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Respuesta exitosa",
                                            summary = "Respuesta de creación de ubicación",
                                            description = "Respuesta con la ubicación recién creada",
                                            value = LOCATION_CREATED_MESSAGE_RESPONSE
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Solicitud inválida (por ejemplo, datos nulos o ubicación duplicada)",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Error de validación",
                                            summary = "Ubicación duplicada",
                                            description = "Se intenta crear una ubicación ya registrada",
                                            value = """
                                                {
                                                  "message": "La ubicación ya existe.",
                                                  "time": "2025-04-06T00:00:00.000Z"
                                                }
                                            """
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
                                                  "time": "2025-04-06T00:00:00.000Z"
                                                }
                                            """
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<SaveLocationResponse> save(@RequestBody SaveLocationRequest saveLocationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(locationService.save(saveLocationRequest));
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar ubicaciones", description = "Busca ubicaciones por nombre de ciudad o departamento")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista paginada de ubicaciones",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Ejemplo de respuesta paginada",
                                    value = SwaggerExamples.PAGED_LOCATION_RESPONSE
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

    })
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
        return ResponseEntity.ok(response);
    }
}
