package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report;



import java.math.BigDecimal;

public record PendingPaymentPatientsReportDTO(
        Long patientId,
        String name,
        Long pendingPayments,
        BigDecimal totalPending
) {
}
