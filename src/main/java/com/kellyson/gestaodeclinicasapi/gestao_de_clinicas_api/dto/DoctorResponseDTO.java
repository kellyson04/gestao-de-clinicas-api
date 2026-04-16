package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.enums.DoctorSpecialty;
import lombok.Builder;

@Builder
public record DoctorResponseDTO(
        String name,
        DoctorSpecialty specialty
) {
}
