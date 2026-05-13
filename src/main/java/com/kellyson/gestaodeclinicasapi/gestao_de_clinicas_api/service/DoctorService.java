package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.service;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.DoctorFiltersRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.DoctorRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.DoctorResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Doctor;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.enums.DoctorSpecialty;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.exception.ConflictException;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.exception.DoctorNotFoundException;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.mapper.DoctorMapper;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository.DoctorRepository;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.specification.DoctorSpecification;
import org.springframework.data.domain.Page;
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
    public Page<DoctorResponseDTO> listDoctors (DoctorFiltersRequestDTO filtersRequestDTO, Pageable pageable) {
        return doctorRepository.findAll(DoctorSpecification.withFilters(filtersRequestDTO),pageable)
                .map(doctor -> DoctorMapper.mapToResponse(doctor));
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
