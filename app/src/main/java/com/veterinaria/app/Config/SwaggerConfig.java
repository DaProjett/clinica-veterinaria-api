package com.veterinaria.app.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI veterinariaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Sistema Veterinario")
                        .description("Documentación de los endpoints para gestión de citas, dueños y mascotas")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Soporte Técnico")
                                .email("soporte@veterinaria.com")));
    }
}
