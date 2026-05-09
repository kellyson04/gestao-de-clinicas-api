package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.service;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository.AppointmentRepository;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private final AppointmentRepository appointmentRepository;
    private final PaymentRepository paymentRepository;

    public ReportService(AppointmentRepository appointmentRepository, PaymentRepository paymentRepository) {
        this.appointmentRepository = appointmentRepository;
        this.paymentRepository = paymentRepository;
    }

    public
}
