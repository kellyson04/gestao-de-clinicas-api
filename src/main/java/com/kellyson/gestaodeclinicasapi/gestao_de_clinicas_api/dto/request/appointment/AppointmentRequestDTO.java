package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.appointment;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AppointmentRequestDTO(
        @NotNull(message = "O ID do Paciente é obrigatório")
        Long patientId,

        @NotNull(message = "O ID do Médico é obrigatório")
        Long doctorId,

        @NotNull(message = "A data da consulta é obrigatória")
        @Future(message = "A data da consulta deve ser no futuro")
        LocalDateTime dateTime
) {
}
