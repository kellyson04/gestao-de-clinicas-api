package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.mapper;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.DoctorRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.DoctorResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Doctor;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DoctorMapper {

    public static Doctor mapToEntity (DoctorRequestDTO doctorRequestDTO) {
        Doctor doctorEntity = Doctor.builder()
                .name(doctorRequestDTO.name())
                .specialty(doctorRequestDTO.specialty())
                .build();

        return doctorEntity;
    }

    public static DoctorResponseDTO mapToResponse (Doctor doctor) {
        DoctorResponseDTO doctorResponse = DoctorResponseDTO.builder()
                .name(doctor.getName())
                .specialty(doctor.getSpecialty())
                .build();

        return doctorResponse;
    }
}
