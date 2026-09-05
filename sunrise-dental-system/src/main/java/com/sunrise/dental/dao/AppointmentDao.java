package com.sunrise.dental.dao;

import com.sunrise.dental.model.Appointment;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface AppointmentDao {
    Appointment save(Appointment appointment);
    Optional<Appointment> findByAppointmentNumber(String appointmentNumber);
    List<Appointment> findAll();
    List<Appointment> findByDate(LocalDate date);
    List<Appointment> searchByPatientName(String name);
    long countForDate(LocalDate date);
    void delete(Long id);
}
