package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.PendingPaymentPatientResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Payment;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.enums.AppointmentStatus;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    boolean existsByAppointmentIdAndStatus (Long appointmentId,PaymentStatus status);

    @Query("""
        SELECT new com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.PendingPaymentPatientResponseDTO(
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
    List<PendingPaymentPatientResponseDTO> listPatientsWithPendingPayment (
            @Param("status") PaymentStatus status
    );
}
