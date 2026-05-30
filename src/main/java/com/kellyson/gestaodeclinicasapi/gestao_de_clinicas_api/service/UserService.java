package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.service;


import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.user.UserAppointmentResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.user.UserProfileResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Appointment;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Doctor;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Patient;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.User;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.enums.AppointmentStatus;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.exception.BadRequestException;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.exception.DoctorNotFoundException;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.exception.PatientNotFoundException;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository.AppointmentRepository;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository.DoctorRepository;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository.PatientRepository;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;



    @Transactional(readOnly = true)
    public UserProfileResponseDTO me(Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();


        return UserProfileResponseDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .active(user.isActive())
                .build();
    }

    @Transactional(readOnly = true)
    public Page<UserAppointmentResponseDTO> myPatientScheduledAppointments(Authentication authentication,Pageable pageable) {

        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

        Patient patient =  user.getPatient();

        if (patient == null) {
            throw new BadRequestException("O Usuario autenticado não esta vinculado a nenhum Paciente");
        }

        Page<Appointment> appointment = appointmentRepository.findByPatientAndStatusAndDateTimeAfter(patient, AppointmentStatus.SCHEDULED,LocalDateTime.now(),pageable);

        return appointment
                .map(app -> UserAppointmentResponseDTO.builder()
                        .patientName(app.getPatient().getName())
                        .doctorName(app.getDoctor().getName())
                        .dateTime(app.getDateTime())
                        .status(app.getStatus())
                        .build());

    }

    @Transactional(readOnly = true)
    public Page<UserAppointmentResponseDTO> myDoctorScheduledAppointments(Authentication authentication,Pageable pageable) {

        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

        Doctor doctor = user.getDoctor();

        if (doctor == null) {
            throw new BadRequestException("O Usuario autenticado não esta vinculado a nenhum Médico");
        }

        Page<Appointment> appointment = appointmentRepository.findByDoctorAndStatusAndDateTimeAfter(doctor, AppointmentStatus.SCHEDULED, LocalDateTime.now(),pageable);

        return appointment
                .map(app -> UserAppointmentResponseDTO.builder()
                        .doctorName(app.getDoctor().getName())
                        .patientName(app.getPatient().getName())
                        .dateTime(app.getDateTime())
                        .status(app.getStatus())
                        .build());

    }

}
