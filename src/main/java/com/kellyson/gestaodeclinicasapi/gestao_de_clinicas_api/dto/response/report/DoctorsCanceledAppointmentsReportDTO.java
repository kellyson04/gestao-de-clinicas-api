package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report;

public record DoctorsCanceledAppointmentsReportDTO(
        Long doctorId,
        String doctorName,
        Long canceled_appointments
) {
}
