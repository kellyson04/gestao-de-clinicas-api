package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.service;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.PatientRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.PatientResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Patient;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.mapper.PatientMapper;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public PatientResponseDTO createPatient (PatientRequestDTO patientRequestDTO) {
        Patient savePatient = patientRepository.save(PatientMapper.mapToEntity(patientRequestDTO));

        return PatientMapper.mapToResponse(savePatient);
    }

    public List<PatientResponseDTO> listPatients () {
        return patientRepository.findAll()
                .stream()
                .map(patient -> PatientMapper.mapToResponse(patient))
                .toList();
    }
}
