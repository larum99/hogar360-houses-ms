package com.hogar360.houses.commons.configurations.config;

public class ControllerConstants {
    private ControllerConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String BASE_URL = "/api/v1/house";
    public static final String SAVE_PATH = "/";
    public static final String TAG = "Casas";
    public static final String TAG_DESCRIPTION = "Operaciones relacionadas con los casas";
    public static final String REST_ENDPOINT_PACKAGE = "com.hogar360.houses.houses.infrastructure.endpoints.rest";
    public static final String SEARCH_PATH = "/search";
}
