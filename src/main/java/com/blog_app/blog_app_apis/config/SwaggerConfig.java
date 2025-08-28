package com.blog_app.blog_app_apis.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Blogging Application API",
        version = "1.0.0",
        description = "REST APIs for Blogging Application. Provides user authentication, blog management, comments, and more.",
        contact = @Contact(
                name = "Kaif Sheikh",
                email = "kaifsheikh0708@gmail.com",
                url = "https://github.com/kaif0708"
        ),
        license = @License(
                name = "Apache 2.0",
                url = "http://www.apache.org/licenses/LICENSE-2.0.html")
        ),
        security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {


}
