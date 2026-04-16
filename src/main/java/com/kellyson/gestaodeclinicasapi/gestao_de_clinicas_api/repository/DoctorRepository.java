package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.repository;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Doctor;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.enums.DoctorSpecialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Long> {
    List<Doctor> findBySpecialty(DoctorSpecialty specialty);
}
