package com.hogar360.houses.commons.configurations.config;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.hogar360.houses.commons.configurations.config.SwaggerExamples.*;

public class CategoryControllerDocs {

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
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
    public @interface SaveCategoryDoc {}

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
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
    public @interface ListCategoriesDoc {}
}
