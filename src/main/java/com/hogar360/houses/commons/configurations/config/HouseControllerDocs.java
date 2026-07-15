package com.hogar360.houses.commons.configurations.config;

import com.hogar360.houses.houses.application.dto.request.SaveHouseRequest;
import com.hogar360.houses.houses.application.dto.response.HouseResponse;
import com.hogar360.houses.houses.application.dto.response.HouseSimpleResponse;
import com.hogar360.houses.houses.application.dto.response.PagedHouseResponse;
import com.hogar360.houses.houses.application.dto.response.SaveHouseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
    public @interface SaveHouseDoc {}

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Buscar casas",
            description = "Busca casas según los filtros especificados.",
            parameters = {
                    @Parameter(
                            name = "department",
                            description = "Nombre del departamento donde se encuentra la propiedad",
                            example = "Valle"
                    ),
                    @Parameter(
                            name = "category",
                            description = "Categoría de la propiedad (ej. Casa, Apartamento)",
                            example = "Casa"
                    ),
                    @Parameter(
                            name = "price",
                            description = "Precio máximo de la propiedad (las propiedades deben tener un precio menor o igual)",
                            example = "500000"
                    ),
                    @Parameter(
                            name = "bedrooms",
                            description = "Número de dormitorios de la propiedad",
                            example = "4"
                    ),
                    @Parameter(
                            name = "bathrooms",
                            description = "Número de baños de la propiedad",
                            example = "3"
                    ),
                    @Parameter(
                            name = "sortBy",
                            description = "Campo por el cual ordenar los resultados",
                            example = "price"
                    ),
                    @Parameter(
                            name = "sortDirection",
                            description = "Dirección del ordenamiento (asc o desc)",
                            example = "asc"
                    ),
                    @Parameter(
                            name = "page",
                            description = "Número de página a mostrar (comienza en 0)",
                            example = "0"
                    ),
                    @Parameter(
                            name = "size",
                            description = "Cantidad de casas por página",
                            example = "10"
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Casas encontradas exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PagedHouseResponse.class),
                            examples = @ExampleObject(
                                    name = "Respuesta exitosa",
                                    summary = "Casas encontradas",
                                    description = "Respuesta con las casas que cumplen con los filtros",
                                    value = SwaggerExamples.PAGED_HOUSES_RESPONSE
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
    public @interface SearchHousesDoc {}

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Obtener propietario de una casa",
            description = "Retorna el ID del propietario asociado a la casa especificada por su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "ID del propietario encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Long.class),
                            examples = @ExampleObject(
                                    name = "ID propietario",
                                    summary = "Ejemplo de ID devuelto",
                                    description = "ID del propietario de la casa",
                                    value = "12345"
                            )
                    )
            )
    })
    public @interface GetHouseOwnerDoc {}

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Listar casas por ID de publicador",
            description = "Devuelve una lista de casas asociadas al ID del publicador especificado."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de casas encontradas",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = HouseResponse.class))
                    )
            )
    })
    public @interface GetHousesByPublisherDoc {}

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Buscar IDs de casas por ciudad y sector",
            description = "Devuelve una lista de IDs de casas que se encuentran en una ciudad y sector específicos."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de IDs de casas encontrada",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Long.class)),
                            examples = @ExampleObject(
                                    name = "Lista de IDs",
                                    summary = "Ejemplo de IDs de casas",
                                    value = "[101, 102, 103]"
                            )
                    )
            )
    })
    public @interface GetHouseIdsByLocationDoc {}

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Obtener casa por ID",
            description = "Devuelve la información básica de una casa dado su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Casa encontrada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HouseSimpleResponse.class)
                    )
            )
    })
    public @interface GetHouseByIdDoc {}

}
