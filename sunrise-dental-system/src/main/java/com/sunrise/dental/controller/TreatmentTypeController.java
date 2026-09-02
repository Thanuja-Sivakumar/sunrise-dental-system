package com.sunrise.dental.controller;

import com.sunrise.dental.model.TreatmentType;
import com.sunrise.dental.repository.TreatmentTypeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/treatment-types")
public class TreatmentTypeController {

    private final TreatmentTypeRepository treatmentTypeRepository;

    public TreatmentTypeController(TreatmentTypeRepository treatmentTypeRepository) {
        this.treatmentTypeRepository = treatmentTypeRepository;
    }

    @GetMapping
    public List<TreatmentType> getAll() {
        return treatmentTypeRepository.findAll();
    }
}
