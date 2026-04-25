package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response;



import java.math.BigDecimal;

public record PendingPaymentPatientResponseDTO(
        Long patientId,
        String name,
        Long pendingPayments,
        BigDecimal totalPending
) {
}
