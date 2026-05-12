package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.controller;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.ErrorResponse;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report.*;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/clinica/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/top-10-doctors")
    @Operation(summary = "Lista 10 médicos com mais consultas da clinica")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Médicos listados com sucesso")
    })
    public ResponseEntity <List<TopDoctorsByDoneAppointmentsResponseDTO>> findTop10DoctorsByDoneAppointments (Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
               .body(reportService.findTop10DoctorsByDoneAppointments(pageable));
    }

    @GetMapping("/doctors/without-canceled-appointments")
    @Operation(summary = "Lista Médicos que nunca tiveram consulta cancelada")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listagem dos Médicos efetuada com sucesso")
    })
    public ResponseEntity <List<DoctorsWithoutCanceledAppointmentsResponseDTO>> doctorsWithoutCanceledAppointments () {
        return ResponseEntity.status(HttpStatus.OK).body(reportService.doctorsWithoutCanceledAppointments());
    }


    @GetMapping("/doctors/{doctorId}/future-30-appointments")
    @Operation(summary = "Lista as 30 futuras consultas do médico especifico")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listagem das 30 Consultas efetuada com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Médico não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Erro ao Listar as 30 Consultas do Médico, Dados invalidos na requisição",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity <List<DoctorFuture30AppointmentsDTO>> doctorFutureAppointments (
            @Parameter(description = "Usuario manda o ID do Médico no path da requisição")

            @PathVariable Long doctorId) {

        return ResponseEntity.status(HttpStatus.OK)
               .body(reportService.doctorFuture30Appointments(doctorId));
    }


    @GetMapping("/pending/patients")
    @Operation(summary = "Listar Pacientes com Pagamentos Pendentes")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listagem de pacientes com pagamento pendente efetuada com sucesso")
    })
    public ResponseEntity <List<PendingPaymentPatientResponseDTO>> patientsWithPendingPayments (
            @PageableDefault(size = 10) Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
               .body(reportService.patientsWithPendingPayments(pageable));
    }


    @GetMapping("/top-5-doctors-by-revenue")
    @Operation(summary = "Lista os 5 Médicos mais bem pagos da clinica")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listagem dos 5 Médicos mais bem pagos efetuada com sucesso"),
    })
    public ResponseEntity <List<Top5DoctorsByRevenueResponseDTO>> findTop5DoctorsByRevenue (Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
               .body(reportService.findTop5DoctorsByRevenue(pageable));
    }

    @GetMapping("/clinic/annual-profit")
    @Operation(summary = "Mostra faturamento total da clinica no ano e lucro obtido")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Faturamento anual e Lucro apresentados com sucesso"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Ano invalido informado na requisição",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity <ClinicProfitByYearDTO> getClinicProfitByYear (
            @Parameter(description = "Ano usado para calcular o faturamento e lucro da clinica", example = "2026")

            @RequestParam Integer year) {

        return ResponseEntity.status(HttpStatus.OK)
               .body(reportService.getClinicProfitByYear(year));
    }
}
