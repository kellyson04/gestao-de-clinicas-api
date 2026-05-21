package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.auth;

import java.time.LocalDateTime;

public record RegisterResponseDTO(
        String email,
        LocalDateTime registerTime
) {
}
