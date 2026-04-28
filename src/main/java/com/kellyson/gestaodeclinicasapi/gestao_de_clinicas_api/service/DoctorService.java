package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.service;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.DoctorRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.DoctorResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Doctor;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.enums.DoctorSpecialty;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.exception.ConflictException;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.exception.DoctorNotFoundException;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.mapper.DoctorMapper;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository.DoctorRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.print.Doc;
import java.util.List;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public DoctorResponseDTO createDoctor (DoctorRequestDTO doctorRequestDTO) {
        Doctor doctor = Doctor.builder()
                .name(doctorRequestDTO.name())
                .specialty(doctorRequestDTO.specialty())
                .isActive(true)
                .build();

        doctorRepository.save(doctor);

        return DoctorMapper.mapToResponse(doctor);
    }


    @Transactional(readOnly = true)
    public List<DoctorResponseDTO> findBySpecialty (DoctorSpecialty specialty, Pageable pageable) {
        return doctorRepository.findBySpecialty(specialty,pageable)
                .getContent()
                .stream()
                .map(doctor -> DoctorMapper.mapToResponse(doctor))
                .toList();
    }

    @Transactional
    public void softDelete (Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new DoctorNotFoundException("Médico não encontrado."));

        if (!doctor.getIsActive()) {
            throw new ConflictException("O médico ja está inativo.");
        }

        doctor.setIsActive(false);
    }
}
