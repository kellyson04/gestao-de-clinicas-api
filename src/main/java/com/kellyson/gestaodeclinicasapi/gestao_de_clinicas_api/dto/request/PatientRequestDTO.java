package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record PatientRequestDTO(
        @NotBlank
        String name,
        @NotBlank
        @Pattern(regexp = "\\d{11}",message = "O CPF deve conter 11 números")
        String cpf,
        @NotNull
        LocalDate birthDate
) {
}
