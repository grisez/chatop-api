package com.chatop.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    public static final String BEARER_SCHEME = "bearerAuth";

    /**
     * Declares the JWT bearer scheme so Swagger UI's "Authorize" button can attach the
     * token to authenticated calls. Not applied globally: each controller/method that
     * actually requires a token carries its own @SecurityRequirement, so public routes
     * (register, login) don't show a misleading lock icon in the docs.
     */
    @Bean
    public OpenAPI chatopOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("ChâTop API")
                        .version("1.0.0")
                        .description("Backend REST API for the ChâTop rental platform"))
                .components(new Components()
                        .addSecuritySchemes(BEARER_SCHEME, new SecurityScheme()
                                .name(BEARER_SCHEME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}
