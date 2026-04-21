package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Appointment;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.enums.PaymentStatus;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PaymentResponseDTO(
        Appointment appointment,
        BigDecimal amount,
        PaymentStatus status
) {
}
