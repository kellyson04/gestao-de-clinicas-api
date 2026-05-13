package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.controller;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.doc.PatientControllerDoc;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.PatientFiltersRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.PatientRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.AppointmentResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.ErrorResponse;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.PatientResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class PatientController implements PatientControllerDoc {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }


    @Override
    @PostMapping
    public ResponseEntity <PatientResponseDTO> createPatient (
            @Valid @RequestBody PatientRequestDTO patientRequestDTO) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(patientService.createPatient(patientRequestDTO));
    }

    @Override
    @GetMapping
    public ResponseEntity <Page<PatientResponseDTO>> listPatients (
            @ModelAttribute PatientFiltersRequestDTO filtersRequestDTO,
            @PageableDefault(size = 10) Pageable pageable) {

        return ResponseEntity.ok(patientService.listPatients(filtersRequestDTO,pageable));
    }


    @Override
    @GetMapping("/{cpf}")
    public ResponseEntity <PatientResponseDTO> findByCpf (
            @PathVariable String cpf) {

        return ResponseEntity.ok(patientService.findByCpf(cpf));
    }


    @Override
    @PatchMapping("/{patientId}/deactivate")
    public ResponseEntity<Void> deactivePatient (
            @PathVariable Long patientId) {

        patientService.softDelete(patientId);
        return ResponseEntity.noContent().build();
    }
}
