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

        @NotBlank(message = "O nome da cidade é obrigatório")
        @Size(max = 100,message = "O nome da Cidade deve ter no maximo 100 caracteres")
        String city,

        @NotBlank(message = "O nome do estado é obrigatório")
        @Size(max = 2, min = 2, message = "O Estado deve possuir 2 caracteres")
        String state,

        @NotNull(message = "A data de nascimento é obrigatória")
        @Past(message = "A data de nascimento deve estar no passado")
        LocalDate birthDate
) {
}
