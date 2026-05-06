package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response;

public record TopDoctorByDoneAppointmentsResponseDTO(
        Long id,
        String name,
        Long totalAppointments
) {
}
