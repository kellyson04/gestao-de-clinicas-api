package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.patient;

import java.time.LocalDate;

public record PatientFiltersRequestDTO(
        String name,
        String cpf,
        String city,
        String state,
        LocalDate birthStartDate,
        LocalDate birthEndDate
) {
}
