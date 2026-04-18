package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.exception;

public class DoctorNotFoundException extends RuntimeException {
  public DoctorNotFoundException(String message) {
    super(message);
  }
}
