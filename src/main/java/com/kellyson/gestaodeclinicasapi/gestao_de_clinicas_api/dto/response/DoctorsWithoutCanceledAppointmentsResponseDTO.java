package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response;

public record DoctorsWithoutCanceledAppointmentsResponseDTO(
        Long id,
        String nome,
        Long totalAppointments
) {
}
