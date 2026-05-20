package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.doc;

import io.swagger.v3.oas.annotations.tags.Tag;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.patient.PatientFiltersRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.patient.PatientRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.ErrorResponse;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.patient.PatientResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(
        name = "Patients",
        description = "Operações para cadastro, listagem, buscar por cpf e desativação de pacientes"
)
public interface PatientControllerDoc {
    @Operation(summary = "Cadastrar Paciente")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Cadastra um Paciente no sistema"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Erro ao cadastrar Paciente, Dados invalidos na requisição",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<PatientResponseDTO> createPatient(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Usuario manda os dados do Paciente a ser criado",
                    required = true)

            @Valid @RequestBody PatientRequestDTO patientRequestDTO);


    @Operation(summary = "Listar pacientes")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listagem de pacientes efetuada")
    })
    ResponseEntity<Page<PatientResponseDTO>> listPatients(
            @ModelAttribute PatientFiltersRequestDTO filtersRequestDTO,
            Pageable pageable);


    @Operation(summary = "Buscar Paciente pelo CPF")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Paciente encontrado com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Paciente não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<PatientResponseDTO> findByCpf(
            @Parameter(description = "Usuario manda o CPF no path da requisição", required = true)

            @PathVariable String cpf);


    @Operation(summary = "Desativar Paciente")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Paciente desativado com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Paciente não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "409",
                    description = "Paciente ja esta desativado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<Void> deactivePatient(
            @Parameter(description = "Usuario manda o ID do Paciente no path da requisição")

            @PathVariable Long patientId);
}

