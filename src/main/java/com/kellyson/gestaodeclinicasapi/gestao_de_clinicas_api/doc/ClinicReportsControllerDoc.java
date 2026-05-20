package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.doc;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.ErrorResponse;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report.ClinicProfitByYearReportDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report.DoctorFutureAppointmentsReportDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report.DoctorsCanceledAppointmentsReportDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report.DoctorsWithoutCanceledAppointmentsReportDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report.PendingPaymentPatientsReportDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report.Top5DoctorsByRevenueReportDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report.TopDoctorsByDoneAppointmentsReportDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Tag(
        name = "Reports",
        description = "Operações para relatórios de médicos, pacientes, consultas, pagamentos e faturamento da clinica"
)
public interface ClinicReportsControllerDoc {

    @Operation(summary = "Lista 10 médicos com mais consultas da clinica")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Médicos listados com sucesso")
    })
    ResponseEntity <List<TopDoctorsByDoneAppointmentsReportDTO>> findTop10DoctorsByDoneAppointments (Pageable pageable);


    @Operation(summary = "Lista Médicos que nunca tiveram consulta cancelada")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listagem dos Médicos efetuada com sucesso")
    })
    ResponseEntity <Page<DoctorsWithoutCanceledAppointmentsReportDTO>> doctorsWithoutCanceledAppointments (
            Pageable pageable);


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
    ResponseEntity <Page<DoctorFutureAppointmentsReportDTO>> doctorFutureAppointments (
            Pageable pageable,

            @Parameter(description = "Usuario manda o ID do Médico no path da requisição")

            @PathVariable Long doctorId);


    @Operation(summary = "Listar Pacientes com Pagamentos Pendentes")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listagem de pacientes com pagamento pendente efetuada com sucesso")
    })
    ResponseEntity <Page<PendingPaymentPatientsReportDTO>> patientsWithPendingPayments (
            Pageable pageable);


    @Operation(summary = "Lista os 5 Médicos mais bem pagos da clinica")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listagem dos 5 Médicos mais bem pagos efetuada com sucesso"),
    })
    ResponseEntity <List<Top5DoctorsByRevenueReportDTO>> findTop5DoctorsByRevenue (Pageable pageable);


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
    ResponseEntity <ClinicProfitByYearReportDTO> getClinicProfitByYear (
            @Parameter(description = "Ano usado para calcular o faturamento e lucro da clinica", example = "2026")

            @RequestParam Integer year);


    @Operation(summary = "Lista os 50 Médicos com maior número de consultas canceladas")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listagem dos 50 médicos efetuada com sucesso")
    })
    ResponseEntity <List<DoctorsCanceledAppointmentsReportDTO>> listDoctorsWithMostCanceledAppointments ();

}
