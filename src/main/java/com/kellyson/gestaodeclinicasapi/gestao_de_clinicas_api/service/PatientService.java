package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.service;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.PatientFiltersRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.PatientRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.PatientResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Patient;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.exception.ConflictException;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.exception.PatientNotFoundException;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.mapper.PatientMapper;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository.PatientRepository;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.specification.PatientSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Transactional
    public PatientResponseDTO createPatient (PatientRequestDTO patientRequestDTO) {

        if (patientRepository.existsByCpf(patientRequestDTO.cpf())) {
            throw new ConflictException("Ja existe um Paciente com este CPF no sistema");
        }

        Patient patient = PatientMapper.mapToEntity(patientRequestDTO);

        patientRepository.save(patient);

        return PatientMapper.mapToResponse(patient);
    }

    @Transactional(readOnly = true)
    public Page <PatientResponseDTO> listPatients (PatientFiltersRequestDTO filtersRequestDTO, Pageable pageable) {
        return patientRepository.findAll(PatientSpecification.withFilters(filtersRequestDTO),pageable)
                .map(patient -> PatientMapper.mapToResponse(patient));
    }

    @Transactional(readOnly = true)
    public PatientResponseDTO findByCpf (String cpf)  {
        Patient patientByCpf = patientRepository.findByCpf(cpf).orElseThrow(() -> new PatientNotFoundException("Paciente não encontrado"));

        return PatientMapper.mapToResponse(patientByCpf);
    }

    @Transactional
    public void softDelete (Long patientId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new PatientNotFoundException("Paciente não encontrado."));

        if (!patient.getIsActive()) {
            throw new ConflictException("O paciente ja está inativo.");
        }

        patient.setIsActive(false);
    }
}
