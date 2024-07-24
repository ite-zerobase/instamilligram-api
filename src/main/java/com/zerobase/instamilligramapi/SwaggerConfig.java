package com.zerobase.instamilligramapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 공식문서 : https://springdoc.org/#Introduction
// Swagger UI 페이지 : http://localhost:8080/swagger-ui/index.html
// OpenAPI 설명 페이지 (json 형식) : http://localhost:8080/api-docs
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
                .info(new Info().title("Zerobase Instamilligram")
                        .description("Zerobase Instamilligram REST API.")
                        .version("1.0").contact(new Contact().name("Daniel Choi").email( "danielchoi1115@gmail.com").url("github.com/ite-zeobase.com"))
                        .license(new License().name("License of API")
                                .url("API license URL")));
    }

    private Info apiInfo() {
        return new Info()
                .title("Instamilligram API")
                .description("Springdoc Swagger UI")
                .version("1.0.0");
    }
    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }


}