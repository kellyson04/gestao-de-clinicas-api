package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository;


import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report.DoctorFuture30AppointmentsDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report.DoctorsWithoutCanceledAppointmentsResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report.TopDoctorsByDoneAppointmentsResponseDTO;
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
    Page<Appointment> findByPatientAndStatus (Patient patient, AppointmentStatus status,Pageable pageable);
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
    Page<Appointment> findByDoctorAndStatus (Doctor doctor,AppointmentStatus status, Pageable pageable);
    Page<Appointment> findByDoctor(Doctor doctor, Pageable pageable);
    @Query("""
          SELECT new com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report.TopDoctorsByDoneAppointmentsResponseDTO(
            doc.id,
            doc.name,
            COUNT(app.id))
          FROM Appointment app
          JOIN app.doctor doc
          WHERE app.status = 'DONE'
          GROUP BY doc.id,doc.name
          ORDER BY COUNT(app.id) DESC           
          """)
    List<TopDoctorsByDoneAppointmentsResponseDTO> findTop10DoctorsByDoneAppointments (Pageable pageable);
    @Query("""
            SELECT new com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report.DoctorsWithoutCanceledAppointmentsResponseDTO(
               doc.id,
               doc.name,
               COUNT(app.id))
            FROM Appointment app
            JOIN app.doctor doc
            WHERE doc.id NOT IN (
                        SELECT doctor.id
                        FROM Appointment
                        WHERE status = 'CANCELED'
                        )     
            GROUP BY doc.id,doc.name
            """)
    List<DoctorsWithoutCanceledAppointmentsResponseDTO> doctorsWithoutCanceledAppointments ();

    @Query("""
        SELECT new com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.report.DoctorFuture30AppointmentsDTO(
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
    List<DoctorFuture30AppointmentsDTO> findDoctorFuture30Appointments(@Param("doctorId") Long doctorId,
                                                                       Pageable pageable);
}
