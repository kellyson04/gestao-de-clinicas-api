package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.enums.DoctorSpecialty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "doctors")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 100)
    private String name;

    @Column(nullable = false,length = 100)
    @Enumerated(EnumType.STRING)
    private DoctorSpecialty specialty;

    @Column(name = "crm_number",unique = true, nullable = false,length = 11)
    private String crmNumber;

    @Column(name = "crm_uf", nullable = false,length = 2)
    private String crmUf;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_active",nullable = false)
    private Boolean isActive = true;
}
