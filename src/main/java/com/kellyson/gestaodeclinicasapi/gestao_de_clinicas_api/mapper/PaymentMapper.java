package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.mapper;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.PaymentResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Payment;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.enums.PaymentStatus;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PaymentMapper {

    public static PaymentResponseDTO mapToResponse (Payment payment) {
        PaymentResponseDTO paymentResponseDTO = PaymentResponseDTO.builder()
                .appointment(payment.getAppointment())
                .amount(payment.getAmount())
                .status(PaymentStatus.PENDING)
                .build();

        return paymentResponseDTO;
    }

}
