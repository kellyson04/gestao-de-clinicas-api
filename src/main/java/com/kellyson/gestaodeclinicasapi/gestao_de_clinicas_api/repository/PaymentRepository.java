package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report.PendingPaymentPatientResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report.Top5DoctorsByRevenueResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Payment;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.enums.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    boolean existsByAppointmentIdAndStatus (Long appointmentId,PaymentStatus status);
    boolean existsByIdAndAppointmentIdAndStatus(Long paymentId,Long appointmentId,PaymentStatus status);

    @Query("""
        SELECT new com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report.PendingPaymentPatientResponseDTO(
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
    Page<PendingPaymentPatientResponseDTO> listPatientsWithPendingPayment (Pageable pageable);

    @Query("""
           SELECT new com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report.Top5DoctorsByRevenueResponseDTO(
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
    List<Top5DoctorsByRevenueResponseDTO> findTop5DoctorsByRevenue (Pageable pageable);
}
