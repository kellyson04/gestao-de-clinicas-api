package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.exception;

public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException(String message) {
        super(message);
    }
}
