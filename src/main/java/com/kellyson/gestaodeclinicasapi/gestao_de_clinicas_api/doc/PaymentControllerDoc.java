package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.doc;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.PaymentRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.ErrorResponse;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.PaymentResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(
        name = "Payments",
        description = "Operações para registro, confirmação, cancelamento e tentativa de refazer pagamentos"
)
public interface PaymentControllerDoc {

    @Operation(summary = "Registrar Pagamento", description = "Usuario registra o pagamento no sistema")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Pagamento registrado com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Consulta não encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflito na regra de negócio (pagamento inválido)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity <PaymentResponseDTO> registerPayment (
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Usuario envia os dados para registrar pagamento",
                    required = true)

            @Valid @RequestBody PaymentRequestDTO paymentRequestDTO);


    @Operation(summary = "Refazer Pagamento Cancelado",
            description = "Refaz o Pagamento pra consulta caso o antigo tenha status CANCELED")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tentativa de refazer o pagamento efetuada com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Consulta ou pagamento não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflito na regra de negócio (Tentativa de refazer pagamento inválida)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity <PaymentResponseDTO> retryPayment (
            @Parameter(description = "Usuario envia o ID do pagamento no path da requisição", required = true)

            @PathVariable Long paymentId,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Usuario envia os dados para refazer pagamento",
                    required = true)

            @Valid @RequestBody PaymentRequestDTO paymentRequestDTO);


    @Operation(summary = "Confirmar Pagamento", description = "Confirmar pagamento que ficou pendente")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Pagamento confirmado com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Pagamento não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflito na regra de negócio (Tentativa de confirmar pagamento inválida)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<Void> confirmPayment (@PathVariable Long paymentId);


    @Operation(summary = "Cancela pagamento")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Pagamento cancelado com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Pagamento não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(
                    responseCode = "409",
                    description = "Pagamento ja se encontra cancelado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<PaymentResponseDTO> cancelPayment (
            @Parameter(description = "Usuario envia o ID do pagamento no path da requisição")

            @PathVariable Long paymentId);
}


