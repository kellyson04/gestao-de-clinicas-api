package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report;

import java.math.BigDecimal;

public record ClinicProfitByYearDTO(
        BigDecimal totalRevenue,
        BigDecimal clinicCommission
) {
}
