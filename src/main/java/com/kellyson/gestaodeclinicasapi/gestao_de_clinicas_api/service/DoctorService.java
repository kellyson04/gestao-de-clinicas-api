package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.service;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.DoctorRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.DoctorResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Doctor;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.enums.DoctorSpecialty;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.mapper.DoctorMapper;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public DoctorResponseDTO createDoctor (DoctorRequestDTO doctorRequestDTO) {
        Doctor doctor = doctorRepository.save(DoctorMapper.mapToEntity(doctorRequestDTO));

        return DoctorMapper.mapToResponse(doctor);
    }

    public List<DoctorResponseDTO> findBySpecialty (DoctorSpecialty specialty) {
        return doctorRepository.findBySpecialty(specialty)
                .stream()
                .map(doctor -> DoctorMapper.mapToResponse(doctor))
                .toList();
    }
}
