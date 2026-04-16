package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.controller;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.AppointmentRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.AppointmentResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/clinica/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> scheduleAppointment (@Valid @RequestBody AppointmentRequestDTO appointmentRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.scheduleAppointment(appointmentRequestDTO));
    }

    @PatchMapping("/{appointmentId}")
    public ResponseEntity<AppointmentResponseDTO> cancelAppointment (@PathVariable Long appointmentId) {
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.cancelAppointment(appointmentId));
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponseDTO>> listAppoinmentsByPeriod (@RequestParam LocalDateTime firstDate, @RequestParam LocalDateTime lastDate) {
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.listAppointmentsByPeriod(firstDate,lastDate));
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<List<AppointmentResponseDTO>> listPatientAppointments (@PathVariable Long patientId) {
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.listPatientAppointments(patientId));
    }
}
