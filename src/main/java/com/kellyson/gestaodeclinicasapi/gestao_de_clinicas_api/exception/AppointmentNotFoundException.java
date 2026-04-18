package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.exception;

public class AppointmentNotFoundException extends RuntimeException {
  public AppointmentNotFoundException(String message) {
    super(message);
  }
}
