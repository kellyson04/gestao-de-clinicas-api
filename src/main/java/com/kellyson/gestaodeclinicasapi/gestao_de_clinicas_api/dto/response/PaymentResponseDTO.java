package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Appointment;
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
