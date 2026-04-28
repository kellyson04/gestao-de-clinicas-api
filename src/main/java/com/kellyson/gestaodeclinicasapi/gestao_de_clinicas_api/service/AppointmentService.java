package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.service;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.AppointmentRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.AppointmentResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Appointment;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Doctor;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Patient;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.enums.AppointmentStatus;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.exception.*;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.mapper.AppointmentMapper;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository.AppointmentRepository;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository.DoctorRepository;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public AppointmentResponseDTO scheduleAppointment (AppointmentRequestDTO appointmentRequestDTO) {
        Patient patient = patientRepository.findById(appointmentRequestDTO.patientId()).orElseThrow(() -> new PatientNotFoundException("Paciente não encontrado"));
        Doctor doctor = doctorRepository.findById(appointmentRequestDTO.doctorId()).orElseThrow(() -> new DoctorNotFoundException("Médico não encontrado"));

        Appointment appointment = Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .dateTime(appointmentRequestDTO.dateTime())
                .status(AppointmentStatus.SCHEDULED)
                .build();

        if (appointmentRepository.existsByDoctorAndDateTime(doctor,appointment.getDateTime())) {
            throw new ConflictException("Ja existe uma consulta com este médico no mesmo horario");
        }

        if (patient.getIsActive() == false || doctor.getIsActive() == false) {
            throw new ConflictException("Paciente ou Médico se encontra inativo");
        }

        appointmentRepository.save(appointment);

        return AppointmentMapper.mapToResponse(appointment);
    }

    @Transactional
    public AppointmentResponseDTO cancelAppointment (Long id) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new AppointmentNotFoundException("Essa consulta não existe"));

        if (appointment.getStatus().equals(AppointmentStatus.CANCELED)) {
            throw new ConflictException("Consulta ja cancelada");
        }
        if (appointment.getStatus().equals(AppointmentStatus.DONE)) {
            throw new ConflictException("Consulta ja realizada");
        }

        appointment.setStatus(AppointmentStatus.CANCELED);

        return AppointmentMapper.mapToResponse(appointment);
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponseDTO> listAppointmentsByPeriod (LocalDateTime firstDate, LocalDateTime lastDate,Pageable pageable) {
        if (firstDate.isAfter(lastDate) || lastDate.isBefore(firstDate)) {
            throw new BadRequestException("Voce esta colocando uma data invalida");
        }

        Page<Appointment> appointments = appointmentRepository.findAppointmentsByDateTimeBetween(firstDate,lastDate,pageable);

        return appointments.stream()
                .map(appointment -> AppointmentMapper.mapToResponse(appointment))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponseDTO> listPatientScheduledAppointments (Long patientId,Pageable pageable) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new PatientNotFoundException("Paciente não encontrado"));

        Page<Appointment> appointments = appointmentRepository.findByPatientAndStatus(patient,AppointmentStatus.SCHEDULED,pageable);
        return appointments.stream()
                .map(appointment -> AppointmentMapper.mapToResponse(appointment))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponseDTO> listPatientAppointmentsHistory (Long patientId, Pageable pageable) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new PatientNotFoundException("Paciente não encontrado"));

        return appointmentRepository.findByPatient(patient,pageable).stream()
                .map(appointment -> AppointmentMapper.mapToResponse(appointment))
                .toList();

    }

    @Transactional(readOnly = true)
    public List<AppointmentResponseDTO> listDoctorScheduledAppointment (Long doctorId,Pageable pageable) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new DoctorNotFoundException("Médico não encontrado"));

        Page<Appointment> appointments = appointmentRepository.findByDoctorAndStatus(doctor,AppointmentStatus.SCHEDULED,pageable);

        return appointments.stream()
                .map(appointment -> AppointmentMapper.mapToResponse(appointment))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponseDTO> listDoctorAppointmentsHistory (Long doctorId, Pageable pageable) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new DoctorNotFoundException("Médico não encontrado"));

        return appointmentRepository.findByDoctor(doctor,pageable)
                .stream()
                .map(appointment -> AppointmentMapper.mapToResponse(appointment))
                .toList();
    }
}