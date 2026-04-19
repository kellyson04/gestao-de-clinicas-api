package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.service;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.PaymentRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.PaymentResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Appointment;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Payment;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.enums.AppointmentStatus;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.enums.PaymentStatus;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.exception.AppointmentNotFoundException;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.exception.ConflictException;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.mapper.PaymentMapper;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository.AppointmentRepository;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final AppointmentRepository appointmentRepository;

    public PaymentService(PaymentRepository paymentRepository,AppointmentRepository appointmentRepository) {
        this.paymentRepository = paymentRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public PaymentResponseDTO processPayment (PaymentRequestDTO paymentRequestDTO) {
        Appointment appointment = appointmentRepository.findById(paymentRequestDTO.appointmentId()).orElseThrow(() -> new AppointmentNotFoundException("Esta consulta não existe"));

        if (appointment.getStatus().equals(AppointmentStatus.DONE)) {
            throw new ConflictException("Consulta ja realizada, pagamento invalido");
        }

        if (appointment.getStatus().equals(AppointmentStatus.CANCELED)) {
            throw new ConflictException("Esta consulta foi cancelada, pagamento invalido");
        }

        Payment payment = Payment.builder()
                .appointment(appointment)
                .amount(paymentRequestDTO.amount())
                .status(PaymentStatus.PENDING)
                .build();

        if (paymentRepository.existsByAppointmentId(appointment.getId())) {
            payment.setStatus(PaymentStatus.CANCELED);
            throw new ConflictException("Esta consulta ja foi paga.");
        }

        payment.setStatus(PaymentStatus.PAID);
        paymentRepository.save(payment);

       return PaymentMapper.mapToResponse(payment);
    }

    public List<PaymentResponseDTO> listPendentPayment () {
        List<Payment> pendentPayments = paymentRepository.findByStatus(PaymentStatus.PENDING);

        return pendentPayments.stream()
                .map(payment -> PaymentMapper.mapToResponse(payment))
                .toList();
    }
}
