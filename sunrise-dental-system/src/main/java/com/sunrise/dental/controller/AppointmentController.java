package com.sunrise.dental.controller;

import com.sunrise.dental.dto.AppointmentRequest;
import com.sunrise.dental.dto.AppointmentResponse;
import com.sunrise.dental.model.Appointment;
import com.sunrise.dental.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Exposes the clinic's core operations as a REST web service so the system
 * is a genuine distributed application: the browser-based frontend (or any
 * other client - a mobile app, another service, Postman, etc.) communicates
 * with this Spring Boot server purely over HTTP/JSON.
 */
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    // Task 2: Register New Appointment
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentResponse register(@Valid @RequestBody AppointmentRequest request) {
        Appointment saved = appointmentService.registerAppointment(request);
        return AppointmentResponse.from(saved);
    }

    // Task 3: Display Appointment Details - search by appointment number
    @GetMapping("/{appointmentNumber}")
    public AppointmentResponse getByNumber(@PathVariable String appointmentNumber) {
        return AppointmentResponse.from(appointmentService.findByAppointmentNumber(appointmentNumber));
    }

    @GetMapping
    public List<AppointmentResponse> getAll() {
        return appointmentService.findAll().stream()
                .map(AppointmentResponse::from)
                .collect(Collectors.toList());
    }

    @GetMapping("/search")
    public List<AppointmentResponse> search(@RequestParam(required = false) String patientName,
                                             @RequestParam(required = false)
                                             @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE)
                                             LocalDate date) {
        if (patientName != null && !patientName.isBlank()) {
            return appointmentService.searchByPatientName(patientName).stream()
                    .map(AppointmentResponse::from).collect(Collectors.toList());
        }
        if (date != null) {
            return appointmentService.findByDate(date).stream()
                    .map(AppointmentResponse::from).collect(Collectors.toList());
        }
        return appointmentService.findAll().stream()
                .map(AppointmentResponse::from).collect(Collectors.toList());
    }

    @GetMapping("/count")
    public Map<String, Object> countForDate(@RequestParam
                                             @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE)
                                             LocalDate date) {
        return Map.of("date", date, "count", appointmentService.countForDate(date));
    }
}
