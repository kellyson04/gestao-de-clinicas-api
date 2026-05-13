package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.controller;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.doc.DoctorControllerDoc;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.DoctorFiltersRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.DoctorRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.DoctorResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clinica/doctors")
public class DoctorController implements DoctorControllerDoc {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @Override
    @PostMapping
    public ResponseEntity <DoctorResponseDTO> createDoctor (
            @Valid @RequestBody DoctorRequestDTO doctorRequestDTO) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(doctorService.createDoctor(doctorRequestDTO));
    }

    @Override
    @GetMapping
    public ResponseEntity <Page<DoctorResponseDTO>> listDoctors (
            @ModelAttribute DoctorFiltersRequestDTO filtersRequestDTO,

            @PageableDefault(size = 10) Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK).body(doctorService.listDoctors(filtersRequestDTO,pageable));
    }

    @Override
    @PatchMapping("/{doctorId}/deactivate")
    public ResponseEntity<Void> deactiveDoctor (
            @PathVariable Long doctorId) {

        doctorService.softDelete(doctorId);
        return ResponseEntity.noContent().build();
    }
}
