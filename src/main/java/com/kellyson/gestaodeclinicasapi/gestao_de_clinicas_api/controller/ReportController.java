package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.controller;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.PendingPaymentPatientResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.Top5DoctorsByRevenueResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/clinica/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/pending/patients")
    @Operation(summary = "Listar Pacientes com Pagamentos Pendentes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem de pacientes com pagamento pendente efetuada com sucesso")
    })
    public ResponseEntity<List<PendingPaymentPatientResponseDTO>> patientsWithPendingPayments (
            @PageableDefault(size = 10)
            Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(reportService.patientsWithPendingPayments(pageable));
    }


    @GetMapping("/top-5-doctors-by-revenue")
    @Operation(summary = "Lista os 5 Médicos mais bem pagos da clinica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Listagem dos 5 Médicos mais bem pagos efetuada com sucesso"),
            @ApiResponse(responseCode = "400",description = "Erro ao Listar Top 5 Médicos com maior pagamento, Dados invalidos na requisição")
    })
    public ResponseEntity<List<Top5DoctorsByRevenueResponseDTO>> findTop5DoctorsByRevenue (Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(reportService.findTop5DoctorsByRevenue(pageable));
    }

}
