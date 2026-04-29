package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.controller;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.PaymentRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.PaymentResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.PendingPaymentPatientResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.service.PaymentService;
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

import java.util.List;

@RestController
@RequestMapping("/api/clinica/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    @Operation(summary = "Registrar Pagamento", description = "Usuario registra o pagamento no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pagamento registrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Consulta não encontrada"),
            @ApiResponse(responseCode = "409", description = "Conflito na regra de negócio (pagamento inválido)")
    })
    public ResponseEntity <PaymentResponseDTO> registerPayment (
                                               @io.swagger.v3.oas.annotations.parameters.RequestBody
                                               (description = "Usuario envia os dados para registrar pagamento",
                                                required = true)
                                               @Valid @RequestBody PaymentRequestDTO paymentRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.registerPayment(paymentRequestDTO));
    }

    @PatchMapping("/retry/{paymentId}")
    @Operation(summary = "Refazer Pagamento Cancelado",
               description = "Refaz o Pagamento pra consulta caso o antigo tenha status CANCELED")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tentativa de refazer o pagamento efetuada com sucesso"),
            @ApiResponse(responseCode = "404",description = "Consulta ou pagamento não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito na regra de negócio (Tentativa de refazer pagamento inválida)")
    })
    public ResponseEntity<PaymentResponseDTO> retryPayment (
                                              @Parameter(description = "Usuario envia o ID do pagamento no path da requisição",
                                              required = true)
                                              @PathVariable Long paymentId,
                                              @io.swagger.v3.oas.annotations.parameters.RequestBody
                                              (description = "Usuario envia os dados para refazer pagamento",
                                               required = true)
                                              @Valid @RequestBody PaymentRequestDTO paymentRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.retryPayment(paymentId,paymentRequestDTO));
    }

    @PostMapping("/{paymentId}")
    @Operation(summary = "Confirmar Pagamento", description = "Confirmar pagamento que ficou pendente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pagamento confirmado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pagamento não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito na regra de negócio (Tentativa de confirmar pagamento inválida)")
    })
    public ResponseEntity<Void> confirmPayment (@PathVariable Long paymentId) {
        paymentService.confirmPayment(paymentId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/pending")
    @Operation(summary = "Listar Pacientes com Pagamentos Pendentes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem de pacientes com pagamento pendente efetuada com sucesso")
    })
    public ResponseEntity <List<PendingPaymentPatientResponseDTO>> patientsWithPendingPayments (
                                                                    @PageableDefault(size = 10)
                                                                    Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.patientsWithPendingPayments(pageable));
    }
}
