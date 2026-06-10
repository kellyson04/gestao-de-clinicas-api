package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.controller;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.doc.AuthControllerDoc;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.auth.LoginRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.auth.RegisterDoctorRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.auth.RegisterPatientRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.auth.LoginResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.auth.RegisterResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clinica/auth")
@RequiredArgsConstructor
public class AuthController implements AuthControllerDoc {

    private final AuthService authService;

    @Override
    @PostMapping("/register/patient")
    public ResponseEntity<RegisterResponseDTO> registerPatient(
            @RequestBody @Valid RegisterPatientRequestDTO registerPatientRequestDTO) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.registerPatient(registerPatientRequestDTO));
    }


    @Override
    @PostMapping("/register/doctor")
    public ResponseEntity<RegisterResponseDTO> registerDoctor(
            @RequestBody @Valid RegisterDoctorRequestDTO registerDoctorRequestDTO) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.registerDoctor(registerDoctorRequestDTO));
    }


    @Override
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody @Valid LoginRequestDTO loginRequestDTO) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(authService.login(loginRequestDTO));
    }

}
