package com.audion.common.environment;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Audion API Docs",
                description = "청각장애인을 위한 행동보조 프로그램 오디온의 API 문서",
                version = "v1"
        )
)
@Configuration
public class SwaggerConfig {

    private final String BEARER_PREFIX = "Bearer ";

    @Bean
    public OpenAPI openAPI() {
        String kakaoOAuthSchemeName = "KakaoOAuth";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(kakaoOAuthSchemeName);
        Components components = new Components()
                .addSecuritySchemes(kakaoOAuthSchemeName, new SecurityScheme()
                        .name(kakaoOAuthSchemeName)
                        .type(SecurityScheme.Type.OAUTH2)
                        .scheme(BEARER_PREFIX)
                        .bearerFormat(kakaoOAuthSchemeName));
        return new OpenAPI()
                .addSecurityItem(securityRequirement)
                .components(components);
    }

}