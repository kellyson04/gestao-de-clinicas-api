package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.mapper;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.patient.PatientRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.patient.PatientResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Patient;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class PatientMapper {

    public static Patient mapToEntity (PatientRequestDTO patientRequestDTO) {
        Patient patientEntity = Patient.builder()
                .name(patientRequestDTO.name())
                .cpf(patientRequestDTO.cpf())
                .birthDate(patientRequestDTO.birthDate())
                .city(patientRequestDTO.city())
                .state(patientRequestDTO.state())
                .createdAt(LocalDateTime.now())
                .isActive(true)
                .build();

        return patientEntity;
    }

    public static PatientResponseDTO mapToResponse (Patient patient) {
        PatientResponseDTO patientResponse = PatientResponseDTO.builder()
                                             .name(patient.getName())
                                             .state(patient.getState())
                                             .city(patient.getCity())
                                             .birthDate(patient.getBirthDate())
                                             .build();

        return patientResponse;
    }
}
