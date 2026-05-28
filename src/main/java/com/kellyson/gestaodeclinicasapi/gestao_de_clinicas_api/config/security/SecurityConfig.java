package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.config.security;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.config.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        })
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/clinica/auth/**").permitAll()
                        .requestMatchers("/api/clinica/patients", "/api/clinica/patients/**").hasAnyRole("ADMIN", "RECEPTIONIST")
                        .requestMatchers(HttpMethod.GET, "/api/clinica/doctors").authenticated()
                        .requestMatchers("/api/clinica/doctors", "/api/clinica/doctors/**").hasAnyRole("ADMIN", "RECEPTIONIST")
                        .requestMatchers("/api/clinica/appointments", "/api/clinica/appointments/**").hasAnyRole("ADMIN", "RECEPTIONIST")
                        .requestMatchers("/api/clinica/payments", "/api/clinica/payments/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/clinica/reports/doctors/{doctorId}/future-30-appointments").hasAnyRole("ADMIN","RECEPTIONIST")
                        .requestMatchers(HttpMethod.GET, "/api/clinica/reports/pending/patients").hasAnyRole("ADMIN","RECEPTIONIST")
                        .requestMatchers("/api/clinica/reports", "/api/clinica/reports/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter,  UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration) {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

}
