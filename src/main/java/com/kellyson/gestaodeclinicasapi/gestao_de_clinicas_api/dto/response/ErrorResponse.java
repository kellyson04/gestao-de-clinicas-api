package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response;

import lombok.Builder;

@Builder
public record ErrorResponse(
        int status,
        String error,
        String message
) {
}
