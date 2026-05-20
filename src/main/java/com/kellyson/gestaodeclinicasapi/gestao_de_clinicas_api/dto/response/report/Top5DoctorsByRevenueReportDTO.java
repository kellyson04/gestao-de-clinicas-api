package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report;

import java.math.BigDecimal;

public record Top5DoctorsByRevenueReportDTO(
        Long id,
        String name,
        BigDecimal revenue
) {
}
