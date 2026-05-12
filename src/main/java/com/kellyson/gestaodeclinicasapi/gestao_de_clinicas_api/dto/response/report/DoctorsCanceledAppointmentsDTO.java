package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report;

import java.math.BigDecimal;

public record DoctorsCanceledAppointmentsDTO(
        Long doctorId,
        String doctorName,
        Long canceled_appointments
) {
}
