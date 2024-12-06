package com.zerobase.instamilligramapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Value("${spring.server.url}")
    private String serverUrl;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
                .info(new Info().title("Zerobase Instamilligram")
                        .description("Zerobase Instamilligram REST API.")
                        .version("1.0").contact(new Contact().name("Daniel Choi").email( "danielchoi1115@gmail.com").url("github.com/ite-zerobase.com"))
                        .license(new License().name("License of API")
                                .url("API license URL"))
                )
                .servers(List.of(new Server().url(serverUrl).description("Zerobase Instamilligram HTTPS API 서버")));
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