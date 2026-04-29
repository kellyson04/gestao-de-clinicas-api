package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.controller;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.PatientRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.AppointmentResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.PatientResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clinica/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }


    @PostMapping
    @Operation(summary = "Cadastrar Paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastra um Paciente no sistema"),
            @ApiResponse(responseCode = "400", description = "Erro ao cadastrar Paciente, Dados invalidos na requisição")
    })
    public ResponseEntity <PatientResponseDTO> createPatient (
                                               @io.swagger.v3.oas.annotations.parameters.RequestBody
                                               (description = "Usuario manda os dados do Paciente a ser criado",
                                                required = true)
                                               @Valid @RequestBody PatientRequestDTO patientRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.createPatient(patientRequestDTO));
    }

    @GetMapping
    @Operation(summary = "Listar pacientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem de pacientes efetuada")
    })
    public ResponseEntity <List<PatientResponseDTO>> listPatients (
                                                     @PageableDefault(size = 10)
                                                     Pageable pageable) {
        return ResponseEntity.ok(patientService.listPatients(pageable));
    }


    @GetMapping("/{cpf}")
    @Operation(summary = "Buscar Paciente pelo CPF")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado")
    })
    public ResponseEntity <PatientResponseDTO> findByCpf (
                                               @Parameter(description = "Usuario manda o CPF no path da requisição",
                                               required = true)
                                               @PathVariable String cpf) {
        return ResponseEntity.ok(patientService.findByCpf(cpf));
    }


    @PatchMapping("/{patientId}/deactivate")
    @Operation(summary = "Desativar Paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Paciente desativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado"),
            @ApiResponse(responseCode = "409", description = "Paciente ja esta desativado")
    })
    public ResponseEntity<Void> deactivePatient (
                                @Parameter(description = "Usuario manda o ID do Paciente no path da requisição")
                                @PathVariable Long patientId) {
        patientService.softDelete(patientId);
        return ResponseEntity.noContent().build();
    }
}
