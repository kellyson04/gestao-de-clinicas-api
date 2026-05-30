package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository;


import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report.*;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Appointment;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Doctor;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Patient;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.enums.AppointmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
    Page<Appointment> findByPatientAndStatusAndDateTimeAfter (Patient patient, AppointmentStatus status,LocalDateTime now,Pageable pageable);
    boolean existsByDoctorAndDateTime(Doctor doctor, LocalDateTime date);
    @Query("""
            SELECT app 
            FROM Appointment app 
            JOIN FETCH app.patient
            JOIN FETCH app.doctor
            WHERE app.dateTime BETWEEN :startDate AND :endDate
            """)
    Page<Appointment> findAppointmentsByDateTimeBetween(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );
    Page<Appointment> findByPatient(Patient patient, Pageable pageable);
    Page<Appointment> findByDoctorAndStatusAndDateTimeAfter (Doctor doctor,AppointmentStatus status,LocalDateTime now, Pageable pageable);
    Page<Appointment> findByDoctor(Doctor doctor, Pageable pageable);
    @Query("""
          SELECT new com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report.TopDoctorsByDoneAppointmentsReportDTO(
            doc.id,
            doc.name,
            COUNT(app.id))
          FROM Appointment app
          JOIN app.doctor doc
          WHERE app.status = 'DONE'
          GROUP BY doc.id,doc.name
          ORDER BY COUNT(app.id) DESC           
          """)
    List<TopDoctorsByDoneAppointmentsReportDTO> findTop10DoctorsByDoneAppointments (Pageable pageable);
    @Query("""
            SELECT new com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report.DoctorsWithoutCanceledAppointmentsReportDTO(
               doc.id,
               doc.name,
               COUNT(app.id))
            FROM Appointment app
            JOIN app.doctor doc
            WHERE app.status = 'DONE'
            AND NOT EXISTS (
                        SELECT app2.doctor
                        FROM Appointment app2
                        WHERE app2.doctor = doc
                        AND app2.status = 'CANCELED'
                        )     
            GROUP BY doc.id,doc.name
            ORDER BY COUNT(app.id) DESC
            """)
    Page<DoctorsWithoutCanceledAppointmentsReportDTO> findDoctorsWithoutCanceledAppointments (Pageable pageable);

    @Query("""
        SELECT new com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report.DoctorFutureAppointmentsReportDTO(
                doc.id,
                doc.name,
                app.dateTime)
        FROM Appointment app
        JOIN app.doctor doc
        WHERE doc.id = :doctorId
        AND app.status = 'SCHEDULED'
        AND app.dateTime >= CURRENT_TIMESTAMP   
        ORDER BY app.dateTime ASC        
            """)
    Page<DoctorFutureAppointmentsReportDTO> findDoctorFutureAppointments(Pageable pageable, @Param("doctorId") Long doctorId);

    @Query("""
        SELECT new com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report.TodayAppointmentsReportDTO(
                doc.id,
                doc.name,
                pat.id,
                pat.name,
                app.dateTime)
        FROM Appointment app
        JOIN app.patient pat
        JOIN app.doctor doc
        WHERE app.dateTime >= :startOfDay
        AND app.dateTime <= :endOfDay
        AND app.status = 'SCHEDULED'    
        ORDER BY app.dateTime ASC
            """)
    Page<TodayAppointmentsReportDTO> findTodayAppointments (@Param("startOfDay") LocalDateTime start, @Param("endOfDay") LocalDateTime end, Pageable pageable);

    @Query("""
           SELECT new com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report.DoctorsCanceledAppointmentsReportDTO(
                      doc.id,
                      doc.name,
                      COUNT(app.id))
           FROM Appointment app
           JOIN app.doctor doc
           WHERE app.status = 'CANCELED'
           GROUP BY doc.id,doc.name
           ORDER BY COUNT(app.id) DESC           
           """)
    List<DoctorsCanceledAppointmentsReportDTO> findDoctorsWithHighestCanceledAppointments (Pageable pageable);
}
