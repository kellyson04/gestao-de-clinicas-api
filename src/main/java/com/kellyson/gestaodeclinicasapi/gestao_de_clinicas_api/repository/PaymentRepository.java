package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report.ClinicProfitByYearReportDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report.PendingPaymentPatientsReportDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report.Top5DoctorsByRevenueReportDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Payment;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.enums.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    boolean existsByAppointmentIdAndStatus (Long appointmentId,PaymentStatus status);
    boolean existsByIdAndAppointmentIdAndStatus(Long paymentId,Long appointmentId,PaymentStatus status);

    @Query("""
        SELECT new com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report.PendingPaymentPatientsReportDTO(
                pat.id,
                pat.name,
                COUNT(pay.id),
                SUM(pay.amount)
        )
        FROM Payment pay
        JOIN pay.appointment app
        JOIN app.patient pat
        WHERE pay.status = 'PENDING'
        GROUP BY pat.id,pat.name
        """)
    Page<PendingPaymentPatientsReportDTO> listPatientsWithPendingPayment (Pageable pageable);

    @Query("""
           SELECT new com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report.Top5DoctorsByRevenueReportDTO(
                  doc.id,
                  doc.name,
                  SUM(pay.amount)) 
           FROM Payment pay
           JOIN pay.appointment app
           JOIN app.doctor doc
           WHERE pay.status = 'PAID'
           GROUP BY doc.id,doc.name
           ORDER BY SUM(pay.amount) DESC                    
           """)
    List<Top5DoctorsByRevenueReportDTO> findTop5DoctorsByRevenue (Pageable pageable);

    @Query("""
          SELECT new com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report.ClinicProfitByYearReportDTO(
                    COALESCE(SUM(pay.amount),0),
                    COALESCE(SUM(pay.amount),0) * 0.30BD)
          FROM Payment pay
          WHERE pay.createdAt >= :startOfYear
          AND pay.createdAt <= :endOfYear
          AND pay.status = 'PAID'                               
          """)
    ClinicProfitByYearReportDTO findClinicProfitByYear (@Param("startOfYear") LocalDateTime startOfYear,
                                                        @Param("endOfYear") LocalDateTime endOfYear);
}
