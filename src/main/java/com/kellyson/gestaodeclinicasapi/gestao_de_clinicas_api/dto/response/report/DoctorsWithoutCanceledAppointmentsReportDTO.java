package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report;

public record DoctorsWithoutCanceledAppointmentsReportDTO(
        Long id,
        String nome,
        Long totalAppointments
) {
}
