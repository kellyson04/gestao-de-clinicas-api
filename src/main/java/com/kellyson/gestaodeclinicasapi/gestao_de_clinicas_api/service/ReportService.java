package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.service;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report.*;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Doctor;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.enums.AppointmentStatus;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.exception.DoctorNotFoundException;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository.AppointmentRepository;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository.DoctorRepository;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository.PaymentRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    private final AppointmentRepository appointmentRepository;
    private final PaymentRepository paymentRepository;
    private final DoctorRepository doctorRepository;

    public ReportService(AppointmentRepository appointmentRepository, PaymentRepository paymentRepository,DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.paymentRepository = paymentRepository;
        this.doctorRepository = doctorRepository;
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

    @Transactional(readOnly = true)
    public List<DoctorFuture30AppointmentsDTO> doctorFuture30Appointments (Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new DoctorNotFoundException("Médico não encontrado"));

       return appointmentRepository.findDoctorFuture30Appointments(doctorId,PageRequest.of(0,30));
    }

    public ClinicProfitByYearDTO getClinicProfitByYear (Integer year) {
        LocalDateTime startOfYear = LocalDate.of(year,1,1).atStartOfDay();
        LocalDateTime endOfYear = LocalDate.of(year + 1,1,1).atStartOfDay();

       return paymentRepository.findClinicProfitByYear(startOfYear,endOfYear);
    }
}
