package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.mapper;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.doctor.DoctorRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.doctor.DoctorResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Doctor;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class DoctorMapper {

    public static Doctor mapToEntity (DoctorRequestDTO doctorRequestDTO) {
        Doctor doctorEntity = Doctor.builder()
                .name(doctorRequestDTO.name())
                .specialty(doctorRequestDTO.specialty())
                .crmNumber(doctorRequestDTO.crmNumber())
                .crmUf(doctorRequestDTO.crmUf())
                .createdAt(LocalDateTime.now())
                .isActive(true)
                .build();

        return doctorEntity;
    }

    public static DoctorResponseDTO mapToResponse (Doctor doctor) {
        DoctorResponseDTO doctorResponse = DoctorResponseDTO.builder()
                .name(doctor.getName())
                .specialty(doctor.getSpecialty())
                .crmNumber(doctor.getCrmNumber())
                .crmUf(doctor.getCrmUf())
                .build();

        return doctorResponse;
    }
}
