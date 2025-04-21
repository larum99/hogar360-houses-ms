package com.hogar360.houses.houses.infrastructure.security.utils;

import org.springframework.http.HttpMethod;

import java.util.List;

public class SecurityConstants {

    private SecurityConstants() {
        throw new IllegalStateException("Utility class");
    }

    // JWT
    public static final String ROLE_CLAIM = "role";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    // CORS
    public static final String ALLOWED_ORIGIN = "http://localhost:8090";
    public static final List<String> ALLOWED_METHODS = List.of(
            HttpMethod.GET.name(),
            HttpMethod.POST.name(),
            HttpMethod.PUT.name(),
            HttpMethod.DELETE.name()
    );
    public static final List<String> ALLOWED_HEADERS = List.of("*");

    public static final List<String> PUBLIC_PATHS = List.of(
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/api-docs/**",
            "/api-docs",
            "/api/v1/category/**",
            "/api/v1/location/search",
            "/api/v1/house/search"
    );

    public static final String CATEGORY_PROTECTED_PATH = "/api/v1/category/**";
    public static final String LOCATION_PROTECTED_PATH = "/api/v1/location/";
    public static final String HOUSE_PROTECTED_PATH = "/api/v1/house/";

}
