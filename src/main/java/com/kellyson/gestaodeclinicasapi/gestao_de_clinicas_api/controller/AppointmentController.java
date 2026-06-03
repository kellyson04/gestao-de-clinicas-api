package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.controller;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.doc.AppointmentControllerDoc;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.appointment.AppointmentRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.appointment.AppointmentResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report.TodayAppointmentsReportDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.service.AppointmentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/clinica/appointments")
@RequiredArgsConstructor
public class AppointmentController implements AppointmentControllerDoc {

    private final AppointmentService appointmentService;

    @Override
    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> scheduleAppointment (
            @Valid @RequestBody AppointmentRequestDTO appointmentRequestDTO) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(appointmentService.scheduleAppointment(appointmentRequestDTO));
    }

    @Override
    @PatchMapping("/{appointmentId}/cancel")
    public ResponseEntity <AppointmentResponseDTO> cancelAppointment (
            @PathVariable Long appointmentId) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(appointmentService.cancelAppointment(appointmentId));
    }

    @Override
    @GetMapping
    public ResponseEntity <Page<AppointmentResponseDTO>> listAppointmentsByPeriod(
            @PageableDefault(size = 30) Pageable pageable,

            @RequestParam LocalDateTime firstDate,

            @RequestParam LocalDateTime lastDate) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(appointmentService.listAppointmentsByPeriod(firstDate,lastDate,pageable));
    }

    @Override
    @GetMapping("/patients/{patientId}/scheduled")
    public ResponseEntity <Page<AppointmentResponseDTO>> listPatientScheduledAppointments (
            @PageableDefault(size = 5) Pageable pageable,

            @PathVariable Long patientId) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(appointmentService.listPatientScheduledAppointments(patientId,pageable));
    }

    @Override
    @GetMapping("/patients/{patientId}")
    public ResponseEntity <Page<AppointmentResponseDTO>> listPatientAppointmentsHistory (
            @PageableDefault(size = 5) Pageable pageable,

            @PathVariable Long patientId) {
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.listPatientAppointmentsHistory(patientId,pageable));
    }

    @Override
    @GetMapping("/doctors/{doctorId}/scheduled")
    public ResponseEntity <Page<AppointmentResponseDTO>> listDoctorScheduledAppointments (
            @PageableDefault(size = 5) Pageable pageable,

            @PathVariable Long doctorId) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(appointmentService.listDoctorScheduledAppointment(doctorId,pageable));
    }

    @Override
    @GetMapping("/doctors/{doctorId}")
    public ResponseEntity <Page<AppointmentResponseDTO>> listDoctorAppointmentsHistory (
            @PageableDefault(size = 10) Pageable pageable,

            @PathVariable Long doctorId) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(appointmentService.listDoctorAppointmentsHistory(doctorId, pageable));
    }


    @Override
    @PatchMapping("/{appointmentId}/complete")
    public ResponseEntity <AppointmentResponseDTO> completeAppointment (
            @PathVariable Long appointmentId) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(appointmentService.completeAppointment(appointmentId));
    }


    @Override
    @GetMapping("/today")
    public ResponseEntity<Page<TodayAppointmentsReportDTO>> todayScheduledAppointments (
            @PageableDefault(size = 10) Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(appointmentService.todayAppointments(pageable));
    }
}
