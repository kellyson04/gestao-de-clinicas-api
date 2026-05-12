package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PaymentRequestDTO(
        @NotNull(message = "O ID da consulta é obrigatório")
        Long appointmentId,
        @NotNull(message = "Digite o valor da consulta:")
        @Positive(message = "O valor da consulta deve ser maior que 0")
        @Digits(integer = 8,fraction = 2,message = "O valor deve ter no maximo 8 digitos inteiros e 2 casas decimais")
        BigDecimal amount

) {
}
