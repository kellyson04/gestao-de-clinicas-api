package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8081")
                                .description("Ambiente local")
                ))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
                                .name(SECURITY_SCHEME_NAME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Informe o token JWT retornado no login. Exemplo: Bearer eyJhbGciOiJIUzI1NiJ9...")
                        ))
                .info(new Info()
                        .title("Gestão de Clínicas API")
                        .version("1.0")
                        .description("""
                                API REST para gerenciamento de clínicas médicas.

                                Recursos disponíveis:
                                - cadastro e autenticação de usuários;
                                - controle de acesso por roles;
                                - gestão de pacientes, médicos, consultas e pagamentos;
                                - relatórios operacionais e financeiros;
                                - integração com ViaCEP para consulta de endereço.

                                Fluxo para testar rotas protegidas:
                                1. Cadastre um paciente ou médico nas rotas de autenticação.
                                2. Faça login em /api/clinica/auth/login.
                                3. Copie o token JWT retornado.
                                4. Clique em Authorize e informe o token no formato Bearer.

                                Roles utilizadas:
                                ADMIN, RECEPTIONIST, DOCTOR e PATIENT.
                                """)
                        .contact(new Contact()
                                .name("Kellyson Lopes")
                                .url("https://github.com/kellyson04/gestao-de-clinicas-api")
                                .email("kellysonlopes04@outlook.com"))
                );
    }
}
