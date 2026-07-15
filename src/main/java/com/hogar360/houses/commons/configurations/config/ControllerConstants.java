package com.hogar360.houses.commons.configurations.config;

public class ControllerConstants {
    private ControllerConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String BASE_URL_HOUSES = "/api/v1/house";
    public static final String PATH = "/";
    public static final String TAG_HOUSES = "Casas";
    public static final String TAG_DESCRIPTION_HOUSES = "Operaciones relacionadas con las casas";
    public static final String REST_ENDPOINT_PACKAGE = "com.hogar360.houses.houses.infrastructure.endpoints.rest";
    public static final String SEARCH_PATH = "/search";
    public static final String GET_OWNER_PATH = "/{houseId}/owner";
    public static final String GET_BY_PUBLISHER_PATH = "/publisher/{publisherId}";
    public static final String GET_IDS_BY_LOCATION_PATH = "/search-ids-by-location";
    public static final String GET_BY_ID_PATH = "/{houseId}";
    public static final String ROLE_SELLER = "hasRole('VENDEDOR')";
    public static final String BEARER_PREFIX = "Bearer ";

    public static final String BASE_URL_CATEGORIES = "/api/v1/category";
    public static final String TAG_CATEGORIES = "Categorias";
    public static final String TAG_DESCRIPTION_CATEGORIES = "Operaciones relacionadas con las categorias";
    public static final String ROLE_ADMIN = "hasRole('ADMIN')";

    public static final String BASE_URL_LOCATIONS = "/api/v1/location";
    public static final String TAG_LOCATIONS = "Ubicaciones";
    public static final String TAG_DESCRIPTION_LOCATIONS = "Operaciones relacionadas con las ubicaciones";
    public static final String GET_BY_CITY = "/city/{cityId}";
}
