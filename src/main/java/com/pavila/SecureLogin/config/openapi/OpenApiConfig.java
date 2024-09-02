package com.pavila.SecureLogin.config.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Jesús Pablo Avila",
                        url = "https://www.linkedin.com/in/pablo-avila-olivar/",
                        email = "dev.web.jpao@gmail.com"


                ),
                description = "Comprehensive API documentation for the Secure-Login application. This API provides endpoints for user authentication, email verification, password recovery, and secure password management. Ideal for developers integrating with the authentication and user management system.",
                title = "Secure-Login API",
                version = "1.0"

        ),
        servers = {
                @Server(
                        description = "Local",
                        url = "http://localhost:8080/api/v1/"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT authentication scheme. Use 'Bearer' followed by your token.",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}

