package com.hogar360.houses.commons.configurations.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfigSwagger {

    @Bean
    public GroupedOpenApi categoryApi() {
        return GroupedOpenApi.builder().group("category")
                .packagesToScan("com.hogar360.houses.houses.infrastructure.endpoints.rest")
                .build();
    }

    @Bean
    public GroupedOpenApi locationApi() {
        return GroupedOpenApi.builder().group("location")
                .packagesToScan(ControllerConstants.REST_ENDPOINT_PACKAGE)
                .build();
    }

    @Bean
    public GroupedOpenApi houseApi() {
        return GroupedOpenApi.builder().group("house")
                .packagesToScan(ControllerConstants.REST_ENDPOINT_PACKAGE)
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Hogar360 - Houses API")
                        .version("1.0")
                        .description("API para la gestión de propiedades en la plataforma Hogar360"))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .name("Authorization")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)));

    }
}
