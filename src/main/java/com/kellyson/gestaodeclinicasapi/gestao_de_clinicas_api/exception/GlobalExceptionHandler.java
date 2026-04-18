package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.exception;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest (BadRequestException b) {
        ErrorResponse error = ErrorResponse.builder()
                .status(404)
                .error("BAD_REQUEST")
                .message(b.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflictException (ConflictException c) {
        ErrorResponse error = ErrorResponse.builder()
                .status(409)
                .error("CONFLICT")
                .message(c.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePatientNotFoundException (PatientNotFoundException p) {
        ErrorResponse error = ErrorResponse.builder()
                .status(404)
                .error("PATIENT_NOT_FOUND")
                .message(p.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDoctorNotFoundException (DoctorNotFoundException d) {
        ErrorResponse error = ErrorResponse.builder()
                .status(404)
                .error("DOCTOR_NOT_FOUND")
                .message(d.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAppointmentNotFoundException (AppointmentNotFoundException a) {
        ErrorResponse error = ErrorResponse.builder()
                .status(404)
                .error("APPOINTMENT_NOT_FOUND")
                .message(a.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
