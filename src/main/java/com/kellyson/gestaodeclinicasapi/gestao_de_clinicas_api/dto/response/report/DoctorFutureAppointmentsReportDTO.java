package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report;

import java.time.LocalDateTime;

public record DoctorFutureAppointmentsReportDTO(
        Long doctorId,
        String name,
        LocalDateTime appointmentDate
) {
}
