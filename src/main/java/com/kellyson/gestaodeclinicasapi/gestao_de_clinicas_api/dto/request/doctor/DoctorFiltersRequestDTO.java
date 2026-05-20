package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.doctor;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.enums.DoctorSpecialty;

public record DoctorFiltersRequestDTO(
        String name,
        String crmUf,
        DoctorSpecialty specialty

) {
}
