package com.hogar360.houses.commons.configurations.config;

import com.hogar360.houses.houses.application.dto.request.SaveHouseRequest;
import com.hogar360.houses.houses.application.dto.response.SaveHouseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class HouseControllerDocs {

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Guardar (crear) una casa",
            description = "Crea una nueva casa asociada a una categoría y ubicación válidas.",
            requestBody = @RequestBody(
                    description = "Datos de la casa a registrar",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SaveHouseRequest.class),
                            examples = @ExampleObject(
                                    name = "Ejemplo de casa",
                                    summary = "Ejemplo de creación de casa",
                                    description = "Petición para crear una casa",
                                    value = SwaggerExamples.SAVE_HOUSE_REQUEST
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Casa creada exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SaveHouseResponse.class),
                            examples = @ExampleObject(
                                    name = "Respuesta exitosa",
                                    summary = "Casa creada",
                                    description = "Respuesta con ID de la nueva casa",
                                    value = SwaggerExamples.HOUSE_CREATED_RESPONSE
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category o Location no encontrada",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Error de validación",
                                    summary = "Categoría o ubicación no válidas",
                                    description = "Error cuando no existe la categoría o la ubicación",
                                    value = SwaggerExamples.CATEGORY_OR_LOCATION_NOT_FOUND
                            )
                    )
            )
    })
    public @interface SaveHouseDoc {}
}
