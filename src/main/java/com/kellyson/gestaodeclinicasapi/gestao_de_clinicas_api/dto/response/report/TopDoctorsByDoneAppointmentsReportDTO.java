package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report;

public record TopDoctorsByDoneAppointmentsReportDTO(
        Long id,
        String name,
        Long totalAppointments
) {
}
