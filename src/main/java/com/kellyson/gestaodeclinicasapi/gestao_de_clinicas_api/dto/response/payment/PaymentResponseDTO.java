package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.payment;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.enums.PaymentStatus;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PaymentResponseDTO(
        Long appointmentId,
        String patientName,
        String doctorName,
        BigDecimal amount,
        PaymentStatus status
) {
}
