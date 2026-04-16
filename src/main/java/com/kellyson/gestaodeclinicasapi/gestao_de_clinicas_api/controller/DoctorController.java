package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.controller;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.DoctorRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.DoctorResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.service.DoctorService;
import jakarta.validation.Valid;
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
    public ResponseEntity<DoctorResponseDTO> createDoctor (@Valid @RequestBody DoctorRequestDTO doctorRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.createDoctor(doctorRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<DoctorResponseDTO>> listBySpecialty (@RequestParam String specialty) {
        return ResponseEntity.ok(doctorService.findBySpecialty(specialty));
    }
}
