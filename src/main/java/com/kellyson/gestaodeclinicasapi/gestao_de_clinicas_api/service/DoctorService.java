package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.service;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.doctor.DoctorFiltersRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.doctor.DoctorRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.doctor.DoctorResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Doctor;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.exception.ConflictException;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.exception.DoctorNotFoundException;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.mapper.DoctorMapper;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository.DoctorRepository;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.specification.DoctorSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Transactional
    public DoctorResponseDTO createDoctor (DoctorRequestDTO doctorRequestDTO) {

        if (doctorRepository.existsByCrmNumber(doctorRequestDTO.crmNumber())) {
            throw new ConflictException("Ja existe um Médico com este CRM no sistema");
        }

        Doctor doctor = DoctorMapper.mapToEntity(doctorRequestDTO);

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

        if (!doctor.isActive()) {
            throw new ConflictException("O médico ja está inativo.");
        }

        doctor.setActive(false);
    }
}
