package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.specification;

import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.PatientFiltersRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.dto.request.PatientRequestDTO;
import com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.entity.Patient;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class PatientSpecification {

    public static Specification<Patient> withFilters (PatientFiltersRequestDTO filter) {
        return Specification
                .where(nameContains(filter.name()))
                .and(stateEquals(filter.state()))
                .and(cityContains(filter.city()))
                .and(birthDateBetween(filter.birthStartDate(),filter.birthEndDate()));
    }

    private static Specification<Patient> nameContains(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isBlank()) {
                return null;
            }

            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    private static Specification<Patient> stateEquals(String state) {
        return (root, query, cb) -> {
            if (state == null || state.isBlank()) {
                return null;
            }

            return cb.equal(cb.upper(root.get("state")), state.toUpperCase());
        };
    }

    private static Specification<Patient> cityContains(String city) {
        return (root, query, cb) -> {
            if (city == null || city.isBlank()) {
                return null;
            }

            return cb.like(cb.lower(root.get("city")), "%" + city.toLowerCase() + "%");
        };
    }

    private static Specification<Patient> birthDateBetween(LocalDate startDate,LocalDate endDate) {
        return (root, query, cb) -> {
            if (startDate == null && endDate == null) {
                return null;
            }

            return cb.between((root.get("birthDate")), startDate, endDate);
        };
    }

}
