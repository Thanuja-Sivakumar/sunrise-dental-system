package com.sunrise.dental.repository;

import com.sunrise.dental.model.TreatmentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreatmentTypeRepository extends JpaRepository<TreatmentType, Long> {
}
