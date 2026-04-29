package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.controller;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.AppointmentRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.AppointmentResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    @Operation(summary = "Agendar consulta", description = "Agenda uma Consulta no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Consulta agendada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao agendar Consulta, Dados invalidos na requisição"),
            @ApiResponse(responseCode = "404", description = "Paciente ou Médico não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito na regra de negócio da Consulta")
    })
    public ResponseEntity<AppointmentResponseDTO> scheduleAppointment (
                                                  @io.swagger.v3.oas.annotations.parameters.RequestBody
                                                  (description = "Usuario manda os dados da Consulta a ser agendada",
                                                   required = true)
                                                    @Valid @RequestBody AppointmentRequestDTO appointmentRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.scheduleAppointment(appointmentRequestDTO));
    }

    @PatchMapping("/{appointmentId}")
    @Operation(summary = "Cancelar consulta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta cancelada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Consulta não encontrada"),
            @ApiResponse(responseCode = "409", description = "Consulta ja está cancelada ou não pode ser cancelada")
    })
    public ResponseEntity<AppointmentResponseDTO> cancelAppointment (
                                                  @Parameter(description = "Usuario manda o ID da Consulta no path da requisição",
                                                             required = true)
                                                  @PathVariable Long appointmentId) {
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.cancelAppointment(appointmentId));
    }

    @GetMapping
    @Operation(summary = "Listar consultas por periodo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem por periodo efetuada"),
            @ApiResponse(responseCode = "400", description = "Datas invalidas ou mal formatadas")
    })
    public ResponseEntity<List<AppointmentResponseDTO>> listAppointmentsByPeriod(
                                                        @PageableDefault(size = 30) Pageable pageable,

                                                        @Parameter(description = "Usuario manda a primeira data do periodo",
                                                                   required = true)
                                                        @RequestParam LocalDateTime firstDate,

                                                        @Parameter(description = "Usuario manda a ultima data do periodo",
                                                                   required = true)
                                                        @RequestParam LocalDateTime lastDate) {
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.listAppointmentsByPeriod(firstDate,lastDate,pageable));
    }

    @GetMapping("/{patientId}/scheduled/patients")
    @Operation(summary = "Listar consultas agendadas do Paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consultas agendadas do Paciente listadas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado")
    })
    public ResponseEntity<List<AppointmentResponseDTO>> listPatientScheduledAppointments (
                                                        @PageableDefault(size = 5) Pageable pageable,

                                                        @Parameter(description = "Usuario manda o ID do Paciente no path da requisição",
                                                                   required = true)
                                                        @PathVariable Long patientId) {
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.listPatientScheduledAppointments(patientId,pageable));
    }

    @GetMapping("/{patientId}/patient")
    @Operation(summary = "Listar histórico de consultas do Paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Histórico de consultas do Paciente listado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado")
    })
    public ResponseEntity<List<AppointmentResponseDTO>> listPatientAppointmentsHistory (
                                                        @PageableDefault(size = 5) Pageable pageable,

                                                        @Parameter(description = "Usuario manda o ID do Paciente no path da requisição",
                                                                   required = true)
                                                        @PathVariable Long patientId) {
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.listPatientAppointmentsHistory(patientId,pageable));
    }

    @GetMapping("/{doctorId}/scheduled/doctors")
    @Operation(summary = "Listar consultas agendadas do Médico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consultas agendadas do Médico listadas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Médico não encontrado")
    })
    public ResponseEntity<List<AppointmentResponseDTO>> listDoctorScheduledAppointments (
                                                        @PageableDefault(size = 5) Pageable pageable,

                                                        @Parameter(description = "Usuario manda o ID do Médico no path da requisição",
                                                                   required = true)
                                                        @PathVariable Long doctorId) {
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.listDoctorScheduledAppointment(doctorId,pageable));
    }

    @GetMapping("/{doctorId}/doctor")
    @Operation(summary = "Listar histórico de consultas do Médico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Histórico de consultas do Médico listado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Médico não encontrado")
    })
    public ResponseEntity <List<AppointmentResponseDTO>> listDoctorAppointmentsHistory (
                                                         @PageableDefault(size = 10) Pageable pageable,

                                                         @Parameter(description = "Usuario manda o ID do Médico no path da requisição",
                                                                    required = true)
                                                         @PathVariable Long doctorId) {
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.listDoctorAppointmentsHistory(doctorId, pageable));
    }
}
