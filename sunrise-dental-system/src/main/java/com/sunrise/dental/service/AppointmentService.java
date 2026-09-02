package com.sunrise.dental.service;

import com.sunrise.dental.dao.AppointmentDao;
import com.sunrise.dental.dto.AppointmentRequest;
import com.sunrise.dental.exception.ResourceNotFoundException;
import com.sunrise.dental.model.Appointment;
import com.sunrise.dental.model.Dentist;
import com.sunrise.dental.model.TreatmentType;
import com.sunrise.dental.pattern.singleton.AppointmentNumberGenerator;
import com.sunrise.dental.repository.DentistRepository;
import com.sunrise.dental.repository.TreatmentTypeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Implements Task 2 (Register New Appointment) and Task 3 (Display /
 * Search Appointment Details) from the assessment brief.
 */
@Service
public class AppointmentService {

    private final AppointmentDao appointmentDao;
    private final DentistRepository dentistRepository;
    private final TreatmentTypeRepository treatmentTypeRepository;

    public AppointmentService(AppointmentDao appointmentDao,
                               DentistRepository dentistRepository,
                               TreatmentTypeRepository treatmentTypeRepository) {
        this.appointmentDao = appointmentDao;
        this.dentistRepository = dentistRepository;
        this.treatmentTypeRepository = treatmentTypeRepository;
    }

    public Appointment registerAppointment(AppointmentRequest request) {
        Dentist dentist = dentistRepository.findById(request.getDentistId())
                .orElseThrow(() -> new ResourceNotFoundException("Dentist not found with id: " + request.getDentistId()));

        TreatmentType treatmentType = treatmentTypeRepository.findById(request.getTreatmentTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Treatment type not found with id: " + request.getTreatmentTypeId()));

        Appointment appointment = new Appointment();
        // Unique appointment number generated via the Singleton generator
        appointment.setAppointmentNumber(AppointmentNumberGenerator.getInstance().nextAppointmentNumber());
        appointment.setPatientName(request.getPatientName());
        appointment.setAddress(request.getAddress());
        appointment.setContactNumber(request.getContactNumber());
        appointment.setDentist(dentist);
        appointment.setTreatmentType(treatmentType);
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setEmergency(request.isEmergency());

        return appointmentDao.save(appointment);
    }

    public Appointment findByAppointmentNumber(String appointmentNumber) {
        return appointmentDao.findByAppointmentNumber(appointmentNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No appointment found with number: " + appointmentNumber));
    }

    public List<Appointment> findAll() {
        return appointmentDao.findAll();
    }

    public List<Appointment> findByDate(LocalDate date) {
        return appointmentDao.findByDate(date);
    }

    public List<Appointment> searchByPatientName(String name) {
        return appointmentDao.searchByPatientName(name);
    }

    public long countForDate(LocalDate date) {
        return appointmentDao.countForDate(date);
    }
}
