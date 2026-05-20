package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.patient;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PatientResponseDTO(
        String name,
        String state,
        String city,
        LocalDate birthDate
) {
}
