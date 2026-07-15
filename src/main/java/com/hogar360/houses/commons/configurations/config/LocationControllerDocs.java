package com.hogar360.houses.commons.configurations.config;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.hogar360.houses.commons.configurations.config.SwaggerExamples.*;

public class LocationControllerDocs {

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
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
    public @interface SaveLocationDoc {}

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
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
    public @interface SearchLocationsDoc {}

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    @Operation(
            summary = "Obtener ubicaciones por ciudad",
            description = "Devuelve todas las ubicaciones asociadas a una ciudad específica",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ubicaciones encontradas",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Ubicaciones por ciudad",
                                            summary = "Lista de ubicaciones",
                                            description = "Lista de ubicaciones correspondientes a una ciudad específica",
                                            value = "[1, 2, 3, 4]"
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
    public @interface GetLocationsByCityIdDoc {}
}
