package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.enums.DoctorSpecialty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DoctorRequestDTO(
        @NotBlank(message = "Nome do usuario vazio")
        @Size(min = 3,max = 100,message = "O nome deve ter entre 3 a 100 caracteres")
        String name,
        @NotNull
        DoctorSpecialty specialty
) {
}
