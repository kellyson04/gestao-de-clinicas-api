package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response;

import java.math.BigDecimal;

public record Top5DoctorsByRevenueResponseDTO(
        Long id,
        String name,
        BigDecimal revenue
) {
}
