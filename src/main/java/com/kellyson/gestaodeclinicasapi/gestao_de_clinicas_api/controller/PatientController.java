package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.controller;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.PatientRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.PatientResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clinica/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PatientResponseDTO createPatient (@Valid @RequestBody PatientRequestDTO patientRequestDTO) {
        return patientService.createPatient(patientRequestDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<PatientResponseDTO> listPatients () {
        return patientService.listPatients();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{cpf}")
    public PatientResponseDTO findByCpf (@PathVariable String cpf) {
        return patientService.findByCpf(cpf);
    }
}
