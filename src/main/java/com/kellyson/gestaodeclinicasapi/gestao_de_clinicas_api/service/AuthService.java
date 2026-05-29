package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.service;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.config.security.TokenProvider;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.auth.LoginRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.auth.RegisterDoctorRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.auth.RegisterPatientRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.auth.LoginResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.auth.RegisterPatientResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.auth.RegisterResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Doctor;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Patient;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.User;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.enums.UserRole;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.exception.BadRequestException;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.exception.ConflictException;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.exception.DoctorNotFoundException;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.exception.PatientNotFoundException;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository.DoctorRepository;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository.PatientRepository;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @Transactional
    public RegisterPatientResponseDTO registerPatient (RegisterPatientRequestDTO registerPatientRequestDTO) {

        if (userRepository.existsByEmail(registerPatientRequestDTO.email())) {
            throw new ConflictException("o Email ja esta em uso");
        }

        Patient patient = patientRepository.findByCpf(registerPatientRequestDTO.cpf())
                .orElseThrow(() -> new PatientNotFoundException("Paciente nao encontrado com o CPF informado"));

        if (userRepository.existsByPatientId(patient.getId())) {
            throw new ConflictException("Este paciente ja possui usuario cadastrado");
        }

        User newUser = new User();

        newUser.setName(patient.getName());
        newUser.setEmail(registerPatientRequestDTO.email());
        newUser.setPassword(passwordEncoder.encode(registerPatientRequestDTO.password()));
        newUser.setRole(UserRole.PATIENT);
        newUser.setPatient(patient);

        userRepository.save(newUser);

        return new RegisterPatientResponseDTO(newUser.getEmail(), LocalDateTime.now());
    }

    @Transactional
    public RegisterResponseDTO registerDoctor(RegisterDoctorRequestDTO registerDoctorRequestDTO) {

        if (userRepository.existsByEmail(registerDoctorRequestDTO.email())) {
            throw new ConflictException("o Email ja esta em uso");
        }

        Doctor doctor = doctorRepository.findByCrmNumber(registerDoctorRequestDTO.crmNumber())
                .orElseThrow(() -> new DoctorNotFoundException("O CRM do Médico informado não possui vinculo com esta clinica"));

        if (userRepository.existsByDoctorId(doctor.getId())) {
            throw new ConflictException("Este medico ja possui usuario cadastrado");
        }

        User newUser = new User();

        newUser.setName(doctor.getName());
        newUser.setEmail(registerDoctorRequestDTO.email());
        newUser.setPassword(passwordEncoder.encode(registerDoctorRequestDTO.password()));
        newUser.setRole(UserRole.DOCTOR);
        newUser.setDoctor(doctor);

        userRepository.save(newUser);

        return new RegisterResponseDTO(newUser.getEmail(), LocalDateTime.now());
    }


    @Transactional(readOnly = true)
    public LoginResponseDTO login (LoginRequestDTO loginRequestDTO) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDTO.email(),loginRequestDTO.password());
        try {

            Authentication authenticatedUser = authenticationManager.authenticate(authenticationToken);
            String token = tokenProvider.generateToken(authenticatedUser);

            return new LoginResponseDTO(token);

        }catch (BadCredentialsException e) {
            throw new BadRequestException("Credenciais invalidas");
        }

    }

}
