package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto;

import lombok.Builder;

@Builder
public record PatientResponseDTO(
        String name
) {
}
