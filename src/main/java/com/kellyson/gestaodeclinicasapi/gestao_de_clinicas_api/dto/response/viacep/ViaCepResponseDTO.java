package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.viacep;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ViaCepResponseDTO(
        String cep,
        String logradouro,
        String bairro,
        String localidade,
        String uf,
        String regiao,
        Boolean erro
) {
}
