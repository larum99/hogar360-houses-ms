package com.hogar360.houses.commons.configurations.config;

public class SwaggerExamples {

    private SwaggerExamples() {

    }

    // request bodies

    public static final String SAVE_CATEGORY_REQUEST = """
        {
          "name": "Apartamento",
          "description": "Vivienda ubicada dentro de un edificio de varios pisos"
        }
    """;

    public static final String SAVE_LOCATION_REQUEST = """
        {
          "cityId": 1,
          "departmentId": 1,
          "sector": "Zona Central"
        }
    """;

    // response bodies

    public static final String CATEGORY_CREATED_MESSAGE_RESPONSE = """
        {
          "message": "Category created successfully.",
          "time": "2025-04-06T13:50:43.425884"
        }
    """;

    public static final String CATEGORY_ALREADY_EXISTS_RESPONSE = """
        {
          "message": "The category already exists",
          "timeStamp": "2025-04-06T13:53:09.4793351"
        }
    """;

    public static final String LIST_CATEGORIES_RESPONSE = """
        {
          "content": [
            {
              "id": 1,
              "name": "Apartamento",
              "description": "Propiedad de tipo apartamento"
            },
            {
              "id": 2,
              "name": "Casa",
              "description": "Casa en la ciudad"
            },
            {
              "id": 4,
              "name": "Casa Prueba",
              "description": "Casa de Prueba"
            }
          ],
          "totalElements": 3,
          "totalPages": 1,
          "currentPage": 0,
          "pageSize": 10,
          "isFirst": true,
          "isLast": true
        }
    """;

    public static final String LOCATION_CREATED_MESSAGE_RESPONSE = """
        {
          "message": "Location created successfully.",
          "time": "2025-04-06T13:50:43.425884"
        }
    """;

    public static final String PAGED_LOCATION_RESPONSE = """
        {
          "content": [
            {
              "id": 1,
              "city": {
                "id": 1,
                "name": "Bogotá"
              },
              "department": {
                "id": 1,
                "name": "Cundinamarca"
              },
              "sector": "Zona Sur"
            },
            {
              "id": 2,
              "city": {
                "id": 2,
                "name": "Medellín"
              },
              "department": {
                "id": 2,
                "name": "Antioquia"
              },
              "sector": "Zona Central"
            }
          ],
          "totalElements": 2,
          "totalPages": 1,
          "currentPage": 0,
          "pageSize": 10,
          "isFirst": true,
          "isLast": true
        }
    """;

    public static final String SAVE_HOUSE_REQUEST = """
    {
      "name": "Casa Campestre",
      "description": "Hermosa casa de ensueño frente a las montañas con excelente vista",
      "categoryId": 1,
      "bedrooms": 3,
      "bathrooms": 2,
      "price": 350000.00,
      "locationId": 1,
      "activePublicationDate": "2025-04-13"
    }
""";

    public static final String HOUSE_CREATED_RESPONSE = """
    {
      "message": "House created successfully.",
      "houseId": 42,
      "createdAt": "2025-04-13T10:15:30.000"
    }
""";

    public static final String CATEGORY_OR_LOCATION_NOT_FOUND = """
    {
      "message": "Category or Location not found",
      "timeStamp": "2025-04-12T15:23:01.123456"
    }
""";

    public static final String PAGED_HOUSES_RESPONSE = """
        {
          "content": [
            {
              "id": 1,
              "name": "Casa Campestre",
              "description": "Hermosa casa frente a las montañas",
              "price": 350000.00,
              "location": {
                "city": "Bogotá",
                "department": "Cundinamarca"
              },
              "category": "Casa",
              "bedrooms": 3,
              "bathrooms": 2
            }
          ],
          "totalElements": 1,
          "totalPages": 1,
          "currentPage": 0,
          "pageSize": 10,
          "isFirst": true,
          "isLast": true
        }
    """;


    // query parameters

    public static final String PAGE_DESCRIPTION = "Número de página a mostrar (comienza en 0)";
    public static final String PAGE_EXAMPLE = "0";

    public static final String SIZE_DESCRIPTION = "Cantidad de categorías por página";
    public static final String SIZE_EXAMPLE = "10";

    public static final String ORDER_ASC_DESCRIPTION = "Indica si se debe ordenar ascendentemente por nombre";
    public static final String ORDER_ASC_EXAMPLE = "true";

    public static final String SORT_BY_DESCRIPTION = "Campo por el cual ordenar";
    public static final String SORT_BY_EXAMPLE = "city.name";

    public static final String SORT_DIRECTION_DESCRIPTION = "Dirección del ordenamiento: asc o desc";
    public static final String SORT_DIRECTION_EXAMPLE = "asc";

    public static final String SEARCH_TERM_DESCRIPTION = "Término de búsqueda por ciudad o departamento";
    public static final String SEARCH_TERM_EXAMPLE = "Bogotá";

    public static final String HOUSE_DEPARTMENT_DESCRIPTION = "Nombre del departamento donde se encuentra la propiedad";
    public static final String HOUSE_DEPARTMENT_EXAMPLE = "Valle";

    public static final String HOUSE_CITY_DESCRIPTION = "Nombre de la ciudad donde se encuentra la propiedad";
    public static final String HOUSE_CITY_EXAMPLE = "Cali";

    public static final String HOUSE_SECTOR_DESCRIPTION = "Sector o barrio de la propiedad";
    public static final String HOUSE_SECTOR_EXAMPLE = "Norte";

    public static final String HOUSE_CATEGORY_DESCRIPTION = "Categoría de la propiedad (ej. Casa, Apartamento)";
    public static final String HOUSE_CATEGORY_EXAMPLE = "Casa";

    public static final String HOUSE_BEDROOMS_DESCRIPTION = "Número de dormitorios de la propiedad";
    public static final String HOUSE_BEDROOMS_EXAMPLE = "3";

    public static final String HOUSE_BATHROOMS_DESCRIPTION = "Número de baños de la propiedad";
    public static final String HOUSE_BATHROOMS_EXAMPLE = "2";

    public static final String HOUSE_PRICE_DESCRIPTION = "Precio exacto de la propiedad";
    public static final String HOUSE_PRICE_EXAMPLE = "250000.00";

    public static final String HOUSE_PUBLISHER_ID_DESCRIPTION = "ID del usuario que publicó la propiedad";
    public static final String HOUSE_PUBLISHER_ID_EXAMPLE = "5";

}
