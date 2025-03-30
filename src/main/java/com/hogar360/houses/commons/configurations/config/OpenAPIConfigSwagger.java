package com.hogar360.houses.commons.configurations.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfigSwagger {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Hogar360 - Houses API")
                        .version("1.0")
                        .description("API para la gestión de propiedades en la plataforma Hogar360")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));

    }
}
