package com.sunrise.dental.dao;

import com.sunrise.dental.model.Appointment;
import com.sunrise.dental.repository.AppointmentRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class AppointmentDaoImpl implements AppointmentDao {

    private final AppointmentRepository appointmentRepository;

    public AppointmentDaoImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public Optional<Appointment> findByAppointmentNumber(String appointmentNumber) {
        return appointmentRepository.findByAppointmentNumber(appointmentNumber);
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public List<Appointment> findByDate(LocalDate date) {
        return appointmentRepository.findByAppointmentDate(date);
    }

    @Override
    public List<Appointment> searchByPatientName(String name) {
        return appointmentRepository.findByPatientNameContainingIgnoreCase(name);
    }

    @Override
    public long countForDate(LocalDate date) {
        return appointmentRepository.countByAppointmentDate(date);
    }

    @Override
    public void delete(Long id) {
        appointmentRepository.deleteById(id);
    }
}
