package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.service;


import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.user.UserProfileResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.User;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserDetailsServiceImpl userDetailsService;

    public UserProfileResponseDTO me(Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();


        return UserProfileResponseDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .active(user.isActive())
                .build();
    }

}
