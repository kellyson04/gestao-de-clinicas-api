package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report;

import java.time.LocalDate;

public record DoctorFutureAppointmentsDTO(
        Long doctorId,
        String name,
        LocalDate appointmentDate
) {
}
