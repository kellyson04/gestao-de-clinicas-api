package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.service;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.DoctorsWithoutCanceledAppointmentsResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.PendingPaymentPatientResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.Top5DoctorsByRevenueResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.TopDoctorsByDoneAppointmentsResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository.AppointmentRepository;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository.PaymentRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReportService {

    private final AppointmentRepository appointmentRepository;
    private final PaymentRepository paymentRepository;

    public ReportService(AppointmentRepository appointmentRepository, PaymentRepository paymentRepository) {
        this.appointmentRepository = appointmentRepository;
        this.paymentRepository = paymentRepository;
    }

    @Transactional(readOnly = true)
    public List<TopDoctorsByDoneAppointmentsResponseDTO> findTop10DoctorsByDoneAppointments (Pageable pageable) {

        return appointmentRepository.findTop10DoctorsByDoneAppointments(PageRequest.of(0,10));
    }

    @Transactional(readOnly = true)
    public List<DoctorsWithoutCanceledAppointmentsResponseDTO> doctorsWithoutCanceledAppointments () {
        return appointmentRepository.doctorsWithoutCanceledAppointments();
    }

    @Transactional(readOnly = true)
    public List<PendingPaymentPatientResponseDTO> patientsWithPendingPayments (Pageable pageable) {
        return paymentRepository.listPatientsWithPendingPayment(pageable)
                .getContent();
    }

    @Transactional(readOnly = true)
    public List<Top5DoctorsByRevenueResponseDTO> findTop5DoctorsByRevenue (Pageable pageable) {
        return paymentRepository.findTop5DoctorsByRevenue(PageRequest.of(0,5));
    }
}
