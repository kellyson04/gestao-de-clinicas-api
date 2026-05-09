package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report;

public record TopDoctorsByDoneAppointmentsResponseDTO(
        Long id,
        String name,
        Long totalAppointments
) {
}
