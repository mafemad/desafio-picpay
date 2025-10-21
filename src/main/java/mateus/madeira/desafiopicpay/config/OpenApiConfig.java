package mateus.madeira.desafiopicpay.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    final String securitySchemeName = "bearerAuth";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                )
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .info(new Info()
                .title("API picpay")
                .version("v1.0.0")
                .description("Esta é uma API para exemplificar os endpoints do projeto")
                .license(new License()
                        .name("Apache 2.0")
                        .url("http://springdoc.org")
                )
        );
    }
}
