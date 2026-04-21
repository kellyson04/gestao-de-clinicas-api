package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AppointmentResponseDTO(
        Long id,
        String patientName,
        String doctorName,
        LocalDateTime dateTime
) {
}
