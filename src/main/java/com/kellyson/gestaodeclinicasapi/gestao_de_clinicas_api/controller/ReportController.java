package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.controller;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.doc.ClinicReportsControllerDoc;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report.*;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/clinica/reports")
@RequiredArgsConstructor
public class ReportController implements ClinicReportsControllerDoc {

    private final ReportService reportService;

    @Override
    @GetMapping("/top-10-doctors")
    public ResponseEntity <List<TopDoctorsByDoneAppointmentsReportDTO>> findTop10DoctorsByDoneAppointments (Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(reportService.findTop10DoctorsByDoneAppointments(pageable));
    }

    @Override
    @GetMapping("/doctors/without-canceled-appointments")
    public ResponseEntity <Page<DoctorsWithoutCanceledAppointmentsReportDTO>> doctorsWithoutCanceledAppointments (
            @PageableDefault(size = 20)
            Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(reportService.doctorsWithoutCanceledAppointments(pageable));
    }


    @Override
    @GetMapping("/doctors/{doctorId}/future-30-appointments")
    public ResponseEntity <Page<DoctorFutureAppointmentsReportDTO>> doctorFutureAppointments (
            @PageableDefault(size = 30)
            Pageable pageable,

            @PathVariable Long doctorId) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(reportService.doctorFutureAppointments(pageable,doctorId));
    }


    @Override
    @GetMapping("/pending/patients")
    public ResponseEntity <Page<PendingPaymentPatientsReportDTO>> patientsWithPendingPayments (
            @PageableDefault(size = 30) Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(reportService.patientsWithPendingPayments(pageable));
    }


    @Override
    @GetMapping("/top-5-doctors-by-revenue")
    public ResponseEntity <List<Top5DoctorsByRevenueReportDTO>> findTop5DoctorsByRevenue (Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(reportService.findTop5DoctorsByRevenue(pageable));
    }

    @Override
    @GetMapping("/clinic/annual-profit")
    public ResponseEntity <ClinicProfitByYearReportDTO> getClinicProfitByYear (
            @RequestParam Integer year) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(reportService.getClinicProfitByYear(year));
    }

    @Override
    @GetMapping("/doctors/canceled-appointments")
    public ResponseEntity <List<DoctorsCanceledAppointmentsReportDTO>> listDoctorsWithMostCanceledAppointments () {

        return ResponseEntity.status(HttpStatus.OK)
                .body(reportService.doctorsWithMostCanceledAppointments());
    }
}
