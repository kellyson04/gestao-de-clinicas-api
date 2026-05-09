package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DoctorFuture30AppointmentsDTO(
        Long doctorId,
        String name,
        LocalDateTime appointmentDate
) {
}
