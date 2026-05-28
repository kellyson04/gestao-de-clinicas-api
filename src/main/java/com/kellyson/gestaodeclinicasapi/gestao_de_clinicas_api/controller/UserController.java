package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.controller;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.user.UserAppointmentResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.response.user.UserProfileResponseDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/clinica/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponseDTO> getMe (Authentication authentication) {

        return ResponseEntity.status(HttpStatus.OK).body(userService.me(authentication));
    }

    @GetMapping("/me/my-scheduled-appointments")
    public ResponseEntity<Page<UserAppointmentResponseDTO>> getMyScheduledAppointments(Authentication authentication, Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK).body(userService.myScheduledAppointments(authentication, pageable));
    }
}
