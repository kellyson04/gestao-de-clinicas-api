package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.doc;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.auth.LoginRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.auth.RegisterDoctorRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.auth.RegisterPatientRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.ErrorResponse;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.auth.LoginResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.auth.RegisterResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(
        name = "Auth",
        description = "Operações para registro, login e geração de token JWT"
)
public interface AuthControllerDoc {

    @Operation(
            summary = "Cadastrar usuario Paciente",
            description = "Cadastra um novo usuario no sistema com senha criptografada e perfil de acesso"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Paciente cadastrado com sucesso"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos na requisição",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "409",
                    description = "Email ja cadastrado no sistema",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<RegisterResponseDTO> registerPatient(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Usuário manda os dados necessários para cadastro no sistema",
                    required = true)
            @Valid @RequestBody RegisterPatientRequestDTO registerPatientRequestDTO);


    @Operation(
            summary = "Cadastrar usuario Médico",
            description = "Cadastra um usuario com perfil de medico usando o CRM de um medico ja existente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuario medico cadastrado com sucesso"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados invalidos na requisicao",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Medico nao encontrado com o CRM informado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "409",
                    description = "Email ja cadastrado ou medico ja possui usuario criado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<RegisterResponseDTO> registerDoctor(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Medico manda email, senha e CRM para cadastro no sistema",
                    required = true)
            @Valid @RequestBody RegisterDoctorRequestDTO registerDoctorRequestDTO);


    @Operation(
            summary = "Realizar login",
            description = "Autentica o usuario no sistema e retorna um token JWT para acessar as rotas protegidas"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Login realizado com sucesso"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos na requisição",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "401",
                    description = "Email ou senha inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<LoginResponseDTO> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Usuario manda email e senha para autenticação no sistema",
                    required = true)
            @Valid @RequestBody LoginRequestDTO loginRequestDTO);

}
