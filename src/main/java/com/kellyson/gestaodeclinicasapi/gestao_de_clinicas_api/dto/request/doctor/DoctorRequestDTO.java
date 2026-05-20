package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.doctor;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.enums.DoctorSpecialty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DoctorRequestDTO(
        @NotBlank(message = "Nome do usuario vazio")
        @Size(max = 100,message = "O nome do Médico deve ter no maximo 100 caracteres")
        String name,

        @NotBlank(message = "A UF do CRM é obrigatória")
        @Size(max = 2, min = 2, message = "A UF do CRM deve possuir 2 caracteres")
        @Pattern(
                regexp = "AC|AL|AP|AM|BA|CE|DF|ES|GO|MA|MT|MS|MG|PA|PB|PR|PE|PI|RJ|RN|RS|RO|RR|SC|SP|SE|TO",
                message = "A UF do CRM deve ser uma UF valida, como SP, RJ ou MG"
        )
        String crmUf,

        @NotBlank(message = "O CRM do Médico não pode estar vazio")
        String crmNumber,

        @NotNull(message = "O Médico precisa ter uma especialidade")
        DoctorSpecialty specialty
) {
}
