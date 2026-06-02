package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.service;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.patient.PatientFiltersRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.patient.PatientRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.patient.PatientResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.viacep.ViaCepResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Patient;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.exception.ConflictException;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.exception.PatientNotFoundException;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.mapper.PatientMapper;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository.PatientRepository;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.specification.PatientSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final ViaCepService viaCepService;


    @Transactional
    public PatientResponseDTO createPatient (PatientRequestDTO patientRequestDTO) {

        if (patientRepository.existsByCpf(patientRequestDTO.cpf())) {
            throw new ConflictException("Ja existe um Paciente com este CPF no sistema");
        }

        ViaCepResponseDTO adress = viaCepService.getCepPayload(patientRequestDTO);
        viaCepService.validateCep(adress);

        Patient patient = PatientMapper.mapToEntity(patientRequestDTO,adress);

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

        if (!patient.isActive()) {
            throw new ConflictException("O paciente ja está inativo.");
        }

        patient.setActive(false);
    }
}
