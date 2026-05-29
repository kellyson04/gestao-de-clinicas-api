package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterPatientRequestDTO(

        @NotBlank(message = "O email e obrigatorio")
        @Email(message = "Email invalido")
        String email,

        @NotBlank(message = "O CPF e obrigatorio")
        @Pattern(regexp = "\\d{11}", message = "O CPF deve conter 11 numeros")
        String cpf,

        @NotBlank(message = "A senha e obrigatoria")
        @Size(min = 8, max = 100, message = "A senha deve ter entre 8 e 100 caracteres")
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+$",
                message = "A senha deve conter pelo menos uma letra maiuscula, uma letra minuscula e um numero"
        )
        String password
) {
}
