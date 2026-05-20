package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.mapper;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.payment.PaymentResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Payment;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PaymentMapper {

    public static PaymentResponseDTO mapToResponse (Payment payment) {
        PaymentResponseDTO paymentResponseDTO = PaymentResponseDTO.builder()
                .appointmentId(payment.getAppointment().getId())
                .patientName(payment.getAppointment().getPatient().getName())
                .doctorName(payment.getAppointment().getDoctor().getName())
                .amount(payment.getAmount())
                .status(payment.getStatus())
                .build();

        return paymentResponseDTO;
    }

}
