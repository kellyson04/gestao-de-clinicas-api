package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.controller;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.AppointmentRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.AppointmentResponseDTO;
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
    public ResponseEntity<List<AppointmentResponseDTO>> listAppointmentsByPeriod(@RequestParam LocalDateTime firstDate, @RequestParam LocalDateTime lastDate) {
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.listAppointmentsByPeriod(firstDate,lastDate));
    }

    @GetMapping("/{patientId}/scheduled/patients")
    public ResponseEntity<List<AppointmentResponseDTO>> listPatientScheduledAppointments (@PathVariable Long patientId) {
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.listPatientScheduledAppointments(patientId));
    }

    @GetMapping("/{patientId}/patient")
    public ResponseEntity<List<AppointmentResponseDTO>> listPatientAppointmentsHistory (@PathVariable Long patientId) {
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.listPatientAppointmentsHistory(patientId));
    }

    @GetMapping("/{doctorId}/scheduled/doctors")
    public ResponseEntity<List<AppointmentResponseDTO>> listDoctorScheduledAppointments (@PathVariable Long doctorId) {
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.listDoctorScheduledAppointment(doctorId));
    }

    @GetMapping("/{doctorId}/doctor")
    public ResponseEntity<List<AppointmentResponseDTO>> listDoctorAppointmentsHistory (@PathVariable Long doctorId) {
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.listDoctorAppointmentsHistory(doctorId));
    }
}
