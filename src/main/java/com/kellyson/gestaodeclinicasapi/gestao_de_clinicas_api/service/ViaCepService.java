package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.service;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.patient.PatientRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.viacep.ViaCepResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class ViaCepService {

    private final RestClient restClient;

    public ViaCepResponseDTO getCepPayload(PatientRequestDTO patientRequestDTO) {
        ViaCepResponseDTO adress = restClient.get()
                .uri("/ws/{cep}/json", patientRequestDTO.cep())
                .retrieve()
                .body(ViaCepResponseDTO.class);

        return adress;
    }

    public void validateCep(ViaCepResponseDTO adress) {
        if (adress.erro() != null) {
            throw new BadRequestException("O CEP não existe");
        }
    }
}
