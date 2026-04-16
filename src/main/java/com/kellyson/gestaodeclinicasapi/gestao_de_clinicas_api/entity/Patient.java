package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "patients")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 100)
    private String name;

    @Column(nullable = false,length = 11,unique = true,updatable = false)
    private String cpf;

    @Column(name = "birth_date",nullable = false)
    private LocalDate birthDate;
}
