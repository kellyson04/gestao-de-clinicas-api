package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.service;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.config.TokenProvider;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.auth.LoginRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.auth.RegisterRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.auth.LoginResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.auth.RegisterResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.User;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.enums.UserRole;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.exception.ConflictException;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @Transactional
    public RegisterResponseDTO register (RegisterRequestDTO registerRequestDTO) {

        if (userRepository.existsByEmail(registerRequestDTO.email())) {
            throw new ConflictException("o Email ja esta em uso");
        }

        User newUser = new User();

        newUser.setName(registerRequestDTO.name());
        newUser.setEmail(registerRequestDTO.email());
        newUser.setPassword(passwordEncoder.encode(registerRequestDTO.password()));
        newUser.setRole(UserRole.PATIENT);

        userRepository.save(newUser);

        return new RegisterResponseDTO(newUser.getEmail(), LocalDateTime.now());
    }


    public LoginResponseDTO login (LoginRequestDTO loginRequestDTO) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDTO.email(),loginRequestDTO.password());
        try {

            Authentication authenticatedUser = authenticationManager.authenticate(authenticationToken);
            String token = tokenProvider.generateToken(authenticatedUser);

            return new LoginResponseDTO(token);

        }catch (BadCredentialsException e) {
            throw new RuntimeException("Credenciais invalidas");
        }

    }

}
