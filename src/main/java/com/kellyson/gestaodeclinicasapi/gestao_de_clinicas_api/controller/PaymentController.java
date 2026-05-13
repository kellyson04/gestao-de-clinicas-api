package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.controller;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.doc.PaymentControllerDoc;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.PaymentRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.ErrorResponse;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.PaymentResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class PaymentController implements PaymentControllerDoc {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity <PaymentResponseDTO> registerPayment (
            @Valid @RequestBody PaymentRequestDTO paymentRequestDTO) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(paymentService.registerPayment(paymentRequestDTO));
    }


    @Override
    @PatchMapping("/{paymentId}/retry")
    public ResponseEntity <PaymentResponseDTO> retryPayment (
            @PathVariable Long paymentId,

            @Valid @RequestBody PaymentRequestDTO paymentRequestDTO) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(paymentService.retryPayment(paymentId,paymentRequestDTO));
    }


    @Override
    @PatchMapping("/{paymentId}/confirm")
    public ResponseEntity<Void> confirmPayment (@PathVariable Long paymentId) {

        paymentService.confirmPayment(paymentId);
        return ResponseEntity.noContent().build();
    }


    @Override
    @PatchMapping("/{paymentId}/cancel")
    public ResponseEntity<PaymentResponseDTO> cancelPayment (
            @PathVariable Long paymentId) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(paymentService.cancelPayment(paymentId));
    }
}
