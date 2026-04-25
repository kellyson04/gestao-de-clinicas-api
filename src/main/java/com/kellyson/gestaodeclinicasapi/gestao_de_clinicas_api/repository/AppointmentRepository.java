package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Appointment;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Doctor;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Patient;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
    List<Appointment> findByPatientAndStatus (Patient patient, AppointmentStatus status);
    boolean existsByDoctorAndDateTime(Doctor doctor, LocalDateTime date);
    @Query("""
            SELECT app 
            FROM Appointment app 
            JOIN FETCH app.patient
            JOIN FETCH app.doctor
            WHERE app.dateTime BETWEEN :startDate AND :endDate
            """)
    List<Appointment> findAppointmentsByDateTimeBetween(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
    List<Appointment> findByPatient(Patient patient);
    List<Appointment> findByDoctorAndStatus (Doctor doctor,AppointmentStatus status);
}
