package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report;

import java.math.BigDecimal;

public record DoctorsCanceledAppointments(
        Long doctorId,
        String doctorName,
        BigDecimal canceled_appointments
) {
}
