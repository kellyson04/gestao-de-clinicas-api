package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PaymentRequestDTO(
        @NotNull(message = "o id da consulta não pode estar vazio.")
        Long appointmentId,
        @NotNull(message = "Digite o valor da consulta:")
        @Digits(integer = 8,fraction = 2,message = "O valor deve ter no maximo 8 digitos inteiro e 2 decimais")
        BigDecimal amount

) {
}
