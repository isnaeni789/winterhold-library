package com.winterhold;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI basiliskOpenApi(){

        SecurityRequirement requirement = new SecurityRequirement().addList("bearerAuth");

        SecurityScheme scheme = new SecurityScheme()
                .name("bearerAuth")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        Components jwtComponent = new Components().addSecuritySchemes("bearerAuth", scheme);

        return new OpenAPI().addSecurityItem(requirement).components(jwtComponent);
    }

}
