package com.sunrise.dental.dao;

import com.sunrise.dental.model.Appointment;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * DAO (Data Access Object) pattern.
 *
 * This interface deliberately sits ON TOP of the Spring Data JPA
 * AppointmentRepository rather than exposing the repository directly to the
 * service layer. It decouples business logic from the persistence
 * technology: the service layer only knows about this contract, so the
 * underlying storage (JPA/H2/MySQL, or even a flat-file implementation)
 * could be swapped without touching AppointmentService.
 */
public interface AppointmentDao {
    Appointment save(Appointment appointment);
    Optional<Appointment> findByAppointmentNumber(String appointmentNumber);
    List<Appointment> findAll();
    List<Appointment> findByDate(LocalDate date);
    List<Appointment> searchByPatientName(String name);
    long countForDate(LocalDate date);
    void delete(Long id);
}
