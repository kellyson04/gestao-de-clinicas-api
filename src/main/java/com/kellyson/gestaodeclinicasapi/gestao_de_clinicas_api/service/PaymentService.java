package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.service;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.PaymentRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.PaymentResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.PendingPaymentPatientResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Appointment;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Payment;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.enums.AppointmentStatus;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.enums.PaymentStatus;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.exception.AppointmentNotFoundException;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.exception.ConflictException;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.exception.PaymentNotFoundException;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.mapper.PaymentMapper;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository.AppointmentRepository;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository.PaymentRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final AppointmentRepository appointmentRepository;

    public PaymentService(PaymentRepository paymentRepository,AppointmentRepository appointmentRepository) {
        this.paymentRepository = paymentRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Transactional
    public PaymentResponseDTO registerPayment (PaymentRequestDTO paymentRequestDTO) {
        Appointment appointment = appointmentRepository.findById(paymentRequestDTO.appointmentId()).orElseThrow(() -> new AppointmentNotFoundException("Esta consulta não existe"));

        if (appointment.getStatus().equals(AppointmentStatus.DONE)) {
            throw new ConflictException("Consulta ja realizada, pagamento invalido");
        }

        if (appointment.getStatus().equals(AppointmentStatus.CANCELED)) {
            throw new ConflictException("Esta consulta foi cancelada, pagamento invalido");
        }


        if (paymentRepository.existsByAppointmentIdAndStatus(appointment.getId(), PaymentStatus.PAID)) {
            throw new ConflictException("O Pagamento desta Consulta ja foi realizado!.");
        }
        if (paymentRepository.existsByAppointmentIdAndStatus(appointment.getId(), PaymentStatus.PENDING)) {
            throw new ConflictException("Tentativa de pagamento ja feita, status atual: PENDENTE.");
        }


        Payment payment = Payment.builder()
                .appointment(appointment)
                .amount(paymentRequestDTO.amount())
                .status(PaymentStatus.PENDING)
                .build();

        paymentRepository.save(payment);

       return PaymentMapper.mapToResponse(payment);
    }

    @Transactional
    public void confirmPayment (Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new PaymentNotFoundException("Não existe nenhum pagamento registrado com esse ID"));

        if (payment.getStatus().equals(PaymentStatus.CANCELED)) {
            throw new ConflictException("Não é possivel confirmar o Pagamento pois ja consta como Cancelado.");
        }

        if (payment.getStatus().equals(PaymentStatus.PAID)) {
            throw new ConflictException("Não é possivel confirmar o Pagamento pois ja consta como Pago");
        }



        payment.setStatus(PaymentStatus.PAID);
    }

    @Transactional(readOnly = true)
    public List <PendingPaymentPatientResponseDTO> patientsWithPendingPayments (Pageable pageable) {
        return paymentRepository.listPatientsWithPendingPayment(PaymentStatus.PENDING,pageable)
                .getContent();
    }
}
