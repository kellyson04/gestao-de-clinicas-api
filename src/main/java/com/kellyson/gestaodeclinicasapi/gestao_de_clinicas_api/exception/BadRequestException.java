package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
