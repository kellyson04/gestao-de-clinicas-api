package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.specification;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.DoctorFiltersRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Doctor;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.enums.DoctorSpecialty;
import org.springframework.data.jpa.domain.Specification;

public class DoctorSpecification {

    public static Specification<Doctor> withFilters (DoctorFiltersRequestDTO filtersRequestDTO) {
        return Specification
                .where(nameContains(filtersRequestDTO.name()))
                .and(crmUfEquals(filtersRequestDTO.crmUf()))
                .and(specialtyEquals(filtersRequestDTO.specialty()));
    }

    private static Specification<Doctor> nameContains(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isBlank()) {
                return null;
            }

            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    private static Specification<Doctor> crmUfEquals (String crmUf) {
        return (root, query, cb) -> {
            if (crmUf == null || crmUf.isBlank()) {
                return null;
            }

            return cb.equal(cb.upper(root.get("crmUf")), crmUf.toUpperCase());
        };
    }

    private static Specification<Doctor> specialtyEquals (DoctorSpecialty specialty) {
        return (root, query, cb) -> {
            if (specialty == null) {
                return null;
            }

            return cb.equal(root.get("specialty"), specialty);
        };
    }
}
