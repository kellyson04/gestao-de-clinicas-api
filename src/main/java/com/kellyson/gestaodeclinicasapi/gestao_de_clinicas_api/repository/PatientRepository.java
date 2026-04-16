package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {
    Optional<Patient> findByCpf (String cpf);
}
