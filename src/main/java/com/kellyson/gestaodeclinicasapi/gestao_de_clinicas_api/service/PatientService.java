package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.service;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.PatientRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.PatientResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Patient;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.exception.ConflictException;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.exception.PatientNotFoundException;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.mapper.PatientMapper;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository.PatientRepository;
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

    public PatientResponseDTO createPatient (PatientRequestDTO patientRequestDTO) {

        Patient patient = Patient.builder()
                .name(patientRequestDTO.name())
                .cpf(patientRequestDTO.cpf())
                .birthDate(patientRequestDTO.birthDate())
                .isActive(true)
                .build();

        patientRepository.save(patient);

        return PatientMapper.mapToResponse(patient);
    }

    @Transactional(readOnly = true)
    public List <PatientResponseDTO> listPatients (Pageable pageable) {
        return patientRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(patient -> PatientMapper.mapToResponse(patient))
                .toList();
    }

    public PatientResponseDTO findByCpf (String cpf) throws RuntimeException {
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
