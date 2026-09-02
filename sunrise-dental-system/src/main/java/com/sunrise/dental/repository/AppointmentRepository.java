package com.sunrise.dental.repository;

import com.sunrise.dental.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Optional<Appointment> findByAppointmentNumber(String appointmentNumber);
    List<Appointment> findByAppointmentDate(LocalDate date);
    List<Appointment> findByPatientNameContainingIgnoreCase(String name);
    long countByAppointmentDate(LocalDate date);
}
