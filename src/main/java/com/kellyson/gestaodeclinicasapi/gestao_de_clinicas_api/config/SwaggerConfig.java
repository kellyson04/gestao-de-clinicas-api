package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Gestão de Clínicas API")
                        .version("1.0")
                        .description("""
                                API para gerenciamento de clínicas médicas.

                                Faça o cadastro em /api/clinica/auth/register,
                                depois realize login em /api/clinica/auth/login
                                para gerar o token JWT e acessar as rotas protegidas.

                                A API possui controle de acesso por roles:
                                ADMIN, RECEPTIONIST, DOCTOR e PATIENT.
                                """)
                        .contact(new Contact()
                                .name("Kellyson Lopes")
                                .url("https://github.com/kellyson04/gestao-de-clinicas-api")
                                .email("kellysonlopes04@outlook.com"))
                );
    }
}
