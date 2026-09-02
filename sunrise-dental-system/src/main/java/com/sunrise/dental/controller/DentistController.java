package com.sunrise.dental.controller;

import com.sunrise.dental.model.Dentist;
import com.sunrise.dental.repository.DentistRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dentists")
public class DentistController {

    private final DentistRepository dentistRepository;

    public DentistController(DentistRepository dentistRepository) {
        this.dentistRepository = dentistRepository;
    }

    @GetMapping
    public List<Dentist> getAll() {
        return dentistRepository.findAll();
    }
}
