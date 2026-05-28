package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.user;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.enums.UserRole;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserProfileResponseDTO(
        String name,
        String email,
        UserRole role,
        LocalDateTime createdAt,
        boolean active
) {
}
