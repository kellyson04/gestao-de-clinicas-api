package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.user;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.enums.AppointmentStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserAppointmentResponseDTO(
        String patientName,
        String doctorName,
        LocalDateTime dateTime,
        AppointmentStatus status
) {
}
