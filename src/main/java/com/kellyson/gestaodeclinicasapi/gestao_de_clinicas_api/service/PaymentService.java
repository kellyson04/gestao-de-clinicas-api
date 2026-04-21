package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.service;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.PaymentRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.PaymentResponseDTO;
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

    public PaymentResponseDTO registerPayment (PaymentRequestDTO paymentRequestDTO) {
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


        if (paymentRepository.existsByAppointmentIdAndStatus(appointment.getId(), PaymentStatus.PAID)) {
            throw new ConflictException("Esta consulta ja foi paga.");
        }
        //ter q ver um jeito de aprovar o pendente pq nesse metodo ia ficar meio esquisito
        if (paymentRepository.existsByAppointmentIdAndStatus(appointment.getId(), PaymentStatus.PENDING)) {
            throw new ConflictException("Voce ja fez este pagamento porem ainda esta pendente");
        }
        if (paymentRepository.existsByAppointmentIdAndStatus(appointment.getId(),PaymentStatus.CANCELED)) {
            throw new ConflictException("Pagamento ja cancelado, reagende a consulta e refaça o pagamento");
        }

        paymentRepository.save(payment);

       return PaymentMapper.mapToResponse(payment);
    }

    public void confirmPayment (Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new RuntimeException("Não existe nenhum pagamento registrado com esse ID"));
        Appointment appointment = appointmentRepository.findById(payment.getAppointment().getId()).orElseThrow(() -> new AppointmentNotFoundException("Voce está tentando pagar uma consulta que não existe"));


        if (!paymentRepository.existsByAppointmentIdAndStatus(appointment.getId(),PaymentStatus.PENDING)) {
            throw new RuntimeException("Não existe pagamento pendente para esta consulta");
        }

        payment.setStatus(PaymentStatus.PAID);
        paymentRepository.save(payment);
    }

    public List<PaymentResponseDTO> listPendentPayment () {
        List<Payment> pendentPayments = paymentRepository.findByStatus(PaymentStatus.PENDING);

        return pendentPayments.stream()
                .map(payment -> PaymentMapper.mapToResponse(payment))
                .toList();
    }
}
