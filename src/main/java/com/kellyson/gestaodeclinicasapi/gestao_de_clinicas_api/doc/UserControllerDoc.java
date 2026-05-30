package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.doc;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.ErrorResponse;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.user.UserAppointmentResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.user.UserProfileResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

@Tag(
        name = "Users",
        description = "Operacoes para perfil do usuario autenticado e suas consultas"
)
public interface UserControllerDoc {

    @Operation(
            summary = "Buscar perfil do usuario autenticado",
            description = "Retorna os dados do usuario logado a partir do token JWT enviado na requisicao"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Perfil do usuario retornado com sucesso"),
            @ApiResponse(
                    responseCode = "401",
                    description = "Usuario nao autenticado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuario sem permissao para acessar o recurso",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<UserProfileResponseDTO> getMe(
            @Parameter(hidden = true) Authentication authentication);


    @Operation(
            summary = "Listar consultas agendadas do paciente autenticado",
            description = "Retorna as consultas agendadas vinculadas ao paciente do usuario logado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Consultas agendadas do paciente retornadas com sucesso"),
            @ApiResponse(
                    responseCode = "401",
                    description = "Usuario nao autenticado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuario sem permissao para acessar o recurso",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<Page<UserAppointmentResponseDTO>> getMyPatientScheduledAppointments(
            @Parameter(hidden = true) Authentication authentication,

            Pageable pageable);


    @Operation(
            summary = "Listar consultas agendadas do medico autenticado",
            description = "Retorna as consultas agendadas vinculadas ao medico do usuario logado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Consultas agendadas do medico retornadas com sucesso"),
            @ApiResponse(
                    responseCode = "401",
                    description = "Usuario nao autenticado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuario sem permissao para acessar o recurso",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<Page<UserAppointmentResponseDTO>> getMyDoctorScheduledAppointments(
            @Parameter(hidden = true) Authentication authentication,

            Pageable pageable);
}
