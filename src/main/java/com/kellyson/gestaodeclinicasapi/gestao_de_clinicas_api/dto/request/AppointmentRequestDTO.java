package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AppointmentRequestDTO(
        @NotNull
        Long patientId,
        @NotNull
        Long doctorId,
        LocalDateTime dateTime
) {
}
