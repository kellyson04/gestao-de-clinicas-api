package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response;

public record InvalidFields(
        String field,
        String message
) {
}
