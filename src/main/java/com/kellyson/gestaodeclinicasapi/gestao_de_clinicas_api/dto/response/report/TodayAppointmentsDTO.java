package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report;

import java.time.LocalDateTime;

public record TodayAppointmentsDTO(
        Long doctorId,
        String doctorName,
        Long patientId,
        String patientName,
        LocalDateTime appointmentDateTime
) {
}
