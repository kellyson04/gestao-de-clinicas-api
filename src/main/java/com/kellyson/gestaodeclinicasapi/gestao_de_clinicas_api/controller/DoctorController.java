package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.controller;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.DoctorRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.DoctorResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.enums.DoctorSpecialty;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clinica/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    @Operation(summary = "Cadastrar médico", description = "Cadastra um Médico no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Médico cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao cadastrar Médico, Dados invalidos na requisição")
    })
    public ResponseEntity<DoctorResponseDTO> createDoctor (
                                             @io.swagger.v3.oas.annotations.parameters.RequestBody
                                             (description = "Usuario manda os dados do Médico a ser criado",
                                              required = true)
                                             @Valid @RequestBody DoctorRequestDTO doctorRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.createDoctor(doctorRequestDTO));
    }

    @GetMapping
    @Operation(summary = "Listar Médicos pela sua especialidade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem por especialidade efetuada"),
            @ApiResponse(responseCode = "400", description = "Especialidade invalida")
    })
    public ResponseEntity<List<DoctorResponseDTO>> listBySpecialty (
                                                   @Parameter(description = "Usuario seleciona especialidade do Médico",
                                                   required = true)
                                                   @RequestParam DoctorSpecialty specialty,
                                                   @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(doctorService.findBySpecialty(specialty,pageable));
    }

    @PatchMapping("/{doctorId}/deactivate")
    @Operation(summary = "Desativar Médico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Médico desativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Médico não encontrado"),
            @ApiResponse(responseCode = "409", description = "Médico ja está desativado")
    })
    public ResponseEntity<Void> deactiveDoctor (
                                @Parameter(description = "Usuario manda o ID do Médico no path da requisição",
                                required = true)
                                @PathVariable Long doctorId) {
        doctorService.softDelete(doctorId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
