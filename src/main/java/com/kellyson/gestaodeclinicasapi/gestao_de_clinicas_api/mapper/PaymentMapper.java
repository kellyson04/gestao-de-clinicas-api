package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.mapper;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.PaymentResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Payment;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.enums.PaymentStatus;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PaymentMapper {

    public static PaymentResponseDTO mapToResponse (Payment payment) {
        PaymentResponseDTO paymentResponseDTO = PaymentResponseDTO.builder()
                .appointmentId(payment.getAppointment().getId())
                .patientName(payment.getAppointment().getPatient().getName())
                .doctorName(payment.getAppointment().getDoctor().getName())
                .amount(payment.getAmount())
                .status(PaymentStatus.PENDING)
                .build();

        return paymentResponseDTO;
    }

}
