package com.example.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Restful API deployed on AWS.")
                        .version("v1")
                        .description("Technologies utilized: Spring Boot, JWT, Swagger, JUnit, Docker, Flyway, MySQL")
                        .termsOfService("https://github.com/ABeatrizSC/restful-api-aws")
                        .license(
                                new License()
                                        .name("Apache 2.0")
                                        .url("https://github.com/ABeatrizSC/restful-api-aws     ")
                        )
                );
    }

}