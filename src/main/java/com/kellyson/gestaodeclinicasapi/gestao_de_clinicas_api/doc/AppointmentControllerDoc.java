package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.doc;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.appointment.AppointmentRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.appointment.AppointmentResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.ErrorResponse;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report.TodayAppointmentsReportDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Tag(
        name = "Appointments",
        description = "Operações para agendamento, cancelamento, listagem, histórico e conclusão de consultas"
)
public interface AppointmentControllerDoc {

    @Operation(summary = "Agendar consulta", description = "Agenda uma Consulta no sistema")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Consulta agendada com sucesso"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Erro ao agendar Consulta, Dados invalidos na requisição",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Paciente ou Médico não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflito na regra de negócio da Consulta",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<AppointmentResponseDTO> scheduleAppointment (
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Usuario manda os dados da Consulta a ser agendada",
                    required = true) @Valid @RequestBody AppointmentRequestDTO appointmentRequestDTO);


    @Operation(summary = "Cancelar consulta")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Consulta cancelada com sucesso",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Consulta não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "409",
                    description = "Consulta ja está cancelada ou não pode ser cancelada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity <AppointmentResponseDTO> cancelAppointment (
            @Parameter(description = "Usuario manda o ID da Consulta no path da requisição", required = true)

            @PathVariable Long appointmentId);


    @Operation(summary = "Listar consultas por periodo")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listagem por periodo efetuada"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datas invalidas ou mal formatadas",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity <Page<AppointmentResponseDTO>> listAppointmentsByPeriod(
            Pageable pageable,

            @Parameter(description = "Usuario manda a primeira data do periodo", required = true)

            @RequestParam LocalDateTime firstDate,

            @Parameter(description = "Usuario manda a ultima data do periodo", required = true)

            @RequestParam LocalDateTime lastDate);


    @Operation(summary = "Listar consultas agendadas do Paciente")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Consultas agendadas do Paciente listadas com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Paciente não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity <Page<AppointmentResponseDTO>> listPatientScheduledAppointments (
            Pageable pageable,

            @Parameter(description = "Usuario manda o ID do Paciente no path da requisição", required = true)

            @PathVariable Long patientId);


    @Operation(summary = "Listar histórico de consultas do Paciente")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Histórico de consultas do Paciente listado com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Paciente não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity <Page<AppointmentResponseDTO>> listPatientAppointmentsHistory (
            Pageable pageable,

            @Parameter(description = "Usuario manda o ID do Paciente no path da requisição", required = true)

            @PathVariable Long patientId);


    @Operation(summary = "Listar consultas agendadas do Médico")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Consultas agendadas do Médico listadas com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Médico não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity <Page<AppointmentResponseDTO>> listDoctorScheduledAppointments (
            Pageable pageable,

            @Parameter(description = "Usuario manda o ID do Médico no path da requisição", required = true)

            @PathVariable Long doctorId);


    @Operation(summary = "Listar histórico de consultas do Médico")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Histórico de consultas do Médico listado com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Médico não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity <Page<AppointmentResponseDTO>> listDoctorAppointmentsHistory (
            Pageable pageable,

            @Parameter(description = "Usuario manda o ID do Médico no path da requisição", required = true)

            @PathVariable Long doctorId);


    @Operation(summary = "Muda status de consulta SCHEDULED para DONE")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Consulta realizada com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Consulta não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "409",
                    description = "Consulta ja se encontra com status DONE, ou se encontra CANCELADA",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity <AppointmentResponseDTO> completeAppointment (
            @Parameter(description = "Usuario manda o ID da Consulta no path da requisição" )

            @PathVariable Long appointmentId);


    @Operation(summary = "Listar consultas do dia")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listagem de consultas do dia efetuada"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Parâmetros de paginação inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<Page<TodayAppointmentsReportDTO>> todayScheduledAppointments (
            Pageable pageable);

}
