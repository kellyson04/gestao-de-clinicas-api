package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PatientRequest(
        @NotBlank
        String name,
        @NotBlank
        String cpf,
        @NotNull
        LocalDate birthDate
) {
}
