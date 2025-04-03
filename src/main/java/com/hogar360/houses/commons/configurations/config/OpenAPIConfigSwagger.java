package com.hogar360.houses.commons.configurations.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfigSwagger {

    @Bean
    public GroupedOpenApi categoryApi() {
        return GroupedOpenApi.builder().group("category")
                .packagesToScan("com.hogar360.houses.houses.infraestructure.endpoints.rest") // Escanea el paquete de CategoryController
                .build();
    }

    @Bean
    public GroupedOpenApi locationApi() {
        return GroupedOpenApi.builder().group("location")
                .packagesToScan("com.hogar360.houses.houses.infraestructure.endpoints.rest") // Escanea el paquete de LocationController
                .build();
    }
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Hogar360 - Houses API")
                        .version("1.0")
                        .description("API para la gestión de propiedades en la plataforma Hogar360")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                .description("Documentación oficial")
                .url("https://hogar360.com/docs"));
    }
}
