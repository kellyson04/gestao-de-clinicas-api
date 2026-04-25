package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.enums.DoctorSpecialty;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "is_active",nullable = false)
    private Boolean isActive = true;
}
