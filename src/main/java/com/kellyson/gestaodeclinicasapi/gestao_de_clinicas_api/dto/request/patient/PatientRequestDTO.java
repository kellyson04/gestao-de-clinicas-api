package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.patient;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record PatientRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 100,message = "O nome do Paciente deve ter no maximo 100 caracteres")
        String name,

        @NotBlank(message = "O CPF é obrigatório")
        @Pattern(regexp = "\\d{11}",message = "O CPF deve conter 11 números")
        String cpf,

        @NotBlank(message = "O CEP é obrigatório")
        @Pattern(regexp = "\\d{8}",message = "O CEP deve conter 8 números")
        String cep,

        @NotNull(message = "A data de nascimento é obrigatória")
        @Past(message = "A data de nascimento deve estar no passado")
        LocalDate birthDate
) {
}
