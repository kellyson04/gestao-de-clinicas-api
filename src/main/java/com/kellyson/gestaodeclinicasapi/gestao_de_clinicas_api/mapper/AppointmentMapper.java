package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.mapper;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.AppointmentResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Appointment;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AppointmentMapper {

    public static AppointmentResponseDTO mapToResponse (Appointment appointment) {
        AppointmentResponseDTO appointmentResponseDTO = AppointmentResponseDTO.builder()
                .id(appointment.getId())
                .patientName(appointment.getPatient().getName())
                .doctorName(appointment.getDoctor().getName())
                .dateTime(appointment.getDateTime())
                .build();

        return appointmentResponseDTO;
    }


}
