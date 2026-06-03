package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.service;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report.*;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Doctor;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.exception.DoctorNotFoundException;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository.AppointmentRepository;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository.DoctorRepository;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final AppointmentRepository appointmentRepository;
    private final PaymentRepository paymentRepository;
    private final DoctorRepository doctorRepository;

    @Transactional(readOnly = true)
    public List<TopDoctorsByDoneAppointmentsReportDTO> findTop10DoctorsByDoneAppointments (Pageable pageable) {

        return appointmentRepository.findTop10DoctorsByDoneAppointments(PageRequest.of(0,10));
    }

    @Transactional(readOnly = true)
    public Page<DoctorsWithoutCanceledAppointmentsReportDTO> doctorsWithoutCanceledAppointments (Pageable pageable) {
        return appointmentRepository.findDoctorsWithoutCanceledAppointments(pageable);
    }

    @Transactional(readOnly = true)
    public Page<PendingPaymentPatientsReportDTO> patientsWithPendingPayments (Pageable pageable) {
        return paymentRepository.listPatientsWithPendingPayment(pageable);
    }

    @Transactional(readOnly = true)
    public List<Top5DoctorsByRevenueReportDTO> findTop5DoctorsByRevenue (Pageable pageable) {
        return paymentRepository.findTop5DoctorsByRevenue(PageRequest.of(0,5));
    }

    @Transactional(readOnly = true)
    public Page<DoctorFutureAppointmentsReportDTO> doctorFutureAppointments (Pageable pageable, Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new DoctorNotFoundException("Médico não encontrado"));

       return appointmentRepository.findDoctorFutureAppointments(pageable,doctorId);
    }

    @Transactional(readOnly = true)
    public ClinicProfitByYearReportDTO getClinicProfitByYear (Integer year) {
        LocalDateTime startOfYear = LocalDate.of(year,1,1).atStartOfDay();
        LocalDateTime endOfYear = LocalDate.of(year + 1,1,1).atStartOfDay();

       return paymentRepository.findClinicProfitByYear(startOfYear,endOfYear);
    }

    @Transactional(readOnly = true)
    public List<DoctorsCanceledAppointmentsReportDTO> doctorsWithMostCanceledAppointments () {

        return appointmentRepository.findDoctorsWithHighestCanceledAppointments(PageRequest.of(0,50));
    }
}
