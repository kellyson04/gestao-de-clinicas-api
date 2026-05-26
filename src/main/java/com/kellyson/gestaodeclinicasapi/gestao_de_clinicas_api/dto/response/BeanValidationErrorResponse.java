package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record BeanValidationErrorResponse(
        int status,
        String error,
        List<InvalidFields> fields,
        LocalDateTime timestamp
) {
}
