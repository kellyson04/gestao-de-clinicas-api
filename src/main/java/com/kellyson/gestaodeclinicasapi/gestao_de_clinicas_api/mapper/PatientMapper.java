package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.mapper;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.PatientRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.PatientResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Patient;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PatientMapper {

    public static Patient mapToEntity (PatientRequestDTO patientRequestDTO) {
        Patient patientEntity = Patient.builder()
                .name(patientRequestDTO.name())
                .cpf(patientRequestDTO.cpf())
                .birthDate(patientRequestDTO.birthDate())
                .build();

        return patientEntity;
    }

    public static PatientResponseDTO mapToResponse (Patient patient) {
        PatientResponseDTO patientResponse = PatientResponseDTO.builder()
                                             .name(patient.getName())
                                             .build();

        return patientResponse;
    }
}
