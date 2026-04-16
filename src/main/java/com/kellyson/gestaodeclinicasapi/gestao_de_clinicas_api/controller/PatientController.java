package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.controller;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.PatientRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.PatientResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.service.PatientService;
import jakarta.validation.Valid;
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
    public ResponseEntity<PatientResponseDTO> createPatient (@Valid @RequestBody PatientRequestDTO patientRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.createPatient(patientRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> listPatients () {
        return ResponseEntity.ok(patientService.listPatients());
    }


    @GetMapping("/{cpf}")
    public ResponseEntity<PatientResponseDTO> findByCpf (@PathVariable String cpf) {
        return ResponseEntity.ok(patientService.findByCpf(cpf));
    }
}
