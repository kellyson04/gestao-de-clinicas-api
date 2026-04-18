package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.service;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.AppointmentRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.AppointmentResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Appointment;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Doctor;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Patient;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.enums.AppointmentStatus;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.exception.AppointmentNotFoundException;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.exception.ConflictException;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.exception.DoctorNotFoundException;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.exception.PatientNotFoundException;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.mapper.AppointmentMapper;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository.AppointmentRepository;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository.DoctorRepository;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, PatientRepository patientRepository, DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    public AppointmentResponseDTO scheduleAppointment (AppointmentRequestDTO appointmentRequestDTO) {
        Patient patient = patientRepository.findById(appointmentRequestDTO.patientId()).orElseThrow(() -> new PatientNotFoundException("Paciente não encontrado"));
        Doctor doctor = doctorRepository.findById(appointmentRequestDTO.doctorId()).orElseThrow(() -> new DoctorNotFoundException("Médico não encontrado"));

        Appointment appointment = Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .dateTime(appointmentRequestDTO.dateTime())
                .status(AppointmentStatus.SCHEDULED)
                .build();

        appointmentRepository.save(appointment);

        return AppointmentMapper.mapToResponse(appointment);
    }

    public AppointmentResponseDTO cancelAppointment (Long id) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new AppointmentNotFoundException("Essa consulta não existe"));

        if (appointment.getStatus().equals(AppointmentStatus.CANCELED)) {
            throw new ConflictException("Consulta ja cancelada");
        }
        if (appointment.getStatus().equals(AppointmentStatus.DONE)) {
            throw new ConflictException("Consulta ja realizada");
        }

        appointment.setStatus(AppointmentStatus.CANCELED);

        appointmentRepository.save(appointment);

        return AppointmentMapper.mapToResponse(appointment);
    }

    public List<AppointmentResponseDTO> listAppointmentsByPeriod (LocalDateTime firstDate, LocalDateTime lastDate) {
        List<Appointment> appointments = appointmentRepository.findAll().stream()
                .filter(appointment -> appointment.getDateTime().isAfter(firstDate))
                .filter(appointment -> appointment.getDateTime().isBefore(lastDate))
                .toList();



        return appointments.stream()
                .map(appointment -> AppointmentMapper.mapToResponse(appointment))
                .toList();
    }

    public List<AppointmentResponseDTO> listPatientAppointments (Long patientId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new PatientNotFoundException("Paciente não encontrado"));

        List<Appointment> appointments = appointmentRepository.findByPatientAndStatus(patient,AppointmentStatus.SCHEDULED);

        return appointments.stream()
                .map(appointment -> AppointmentMapper.mapToResponse(appointment))
                .toList();
    }
}