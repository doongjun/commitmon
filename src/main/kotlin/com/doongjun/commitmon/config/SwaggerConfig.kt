package com.doongjun.commitmon.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.security.SecurityScheme
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@OpenAPIDefinition(
    info =
        Info(
            title = "Commitmon API",
            description = "API Documents",
            version = "v1.0.0",
        ),
    security = [SecurityRequirement(name = "Bearer Authentication")],
)
@SecurityScheme(
    name = "Bearer Authentication",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer",
)
@Configuration
class SwaggerConfig {
    @Bean
    fun api(): GroupedOpenApi =
        GroupedOpenApi
            .builder()
            .group("api-v1-definition")
            .pathsToMatch("/api/**")
            .build()
}
