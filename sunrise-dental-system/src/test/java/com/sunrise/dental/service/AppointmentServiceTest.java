package com.sunrise.dental.service;

import com.sunrise.dental.dao.AppointmentDao;
import com.sunrise.dental.dto.AppointmentRequest;
import com.sunrise.dental.exception.ResourceNotFoundException;
import com.sunrise.dental.model.Appointment;
import com.sunrise.dental.model.Dentist;
import com.sunrise.dental.model.TreatmentType;
import com.sunrise.dental.repository.DentistRepository;
import com.sunrise.dental.repository.TreatmentTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock private AppointmentDao appointmentDao;
    @Mock private DentistRepository dentistRepository;
    @Mock private TreatmentTypeRepository treatmentTypeRepository;

    private AppointmentService appointmentService;

    private Dentist dentist;
    private TreatmentType treatmentType;

    @BeforeEach
    void setUp() {
        appointmentService = new AppointmentService(appointmentDao, dentistRepository, treatmentTypeRepository);

        dentist = new Dentist("Dr. Nimal Perera", "General Dentistry");
        dentist.setId(1L);

        treatmentType = new TreatmentType("Dental Checkup", new BigDecimal("1500.00"), new BigDecimal("1000.00"));
        treatmentType.setId(1L);
    }

    private AppointmentRequest validRequest() {
        AppointmentRequest req = new AppointmentRequest();
        req.setPatientName("Kasun Jayasuriya");
        req.setAddress("12 Galle Road, Colombo 03");
        req.setContactNumber("0771234567");
        req.setDentistId(1L);
        req.setTreatmentTypeId(1L);
        req.setAppointmentDate(LocalDate.now().plusDays(1));
        req.setAppointmentTime(LocalTime.of(10, 30));
        return req;
    }

    @Test
    void registerAppointment_savesAppointmentWithGeneratedNumber() {
        when(dentistRepository.findById(1L)).thenReturn(Optional.of(dentist));
        when(treatmentTypeRepository.findById(1L)).thenReturn(Optional.of(treatmentType));
        when(appointmentDao.save(any(Appointment.class))).thenAnswer(inv -> inv.getArgument(0));

        Appointment result = appointmentService.registerAppointment(validRequest());

        assertNotNull(result.getAppointmentNumber());
        assertTrue(result.getAppointmentNumber().startsWith("APT"));
        assertEquals("Kasun Jayasuriya", result.getPatientName());
        assertEquals(dentist, result.getDentist());
        assertEquals(treatmentType, result.getTreatmentType());
        verify(appointmentDao, times(1)).save(any(Appointment.class));
    }

    @Test
    void registerAppointment_throwsWhenDentistDoesNotExist() {
        when(dentistRepository.findById(1L)).thenReturn(Optional.empty());

        AppointmentRequest req = validRequest();
        assertThrows(ResourceNotFoundException.class, () -> appointmentService.registerAppointment(req));
        verify(appointmentDao, never()).save(any());
    }

    @Test
    void registerAppointment_throwsWhenTreatmentTypeDoesNotExist() {
        when(dentistRepository.findById(1L)).thenReturn(Optional.of(dentist));
        when(treatmentTypeRepository.findById(1L)).thenReturn(Optional.empty());

        AppointmentRequest req = validRequest();
        assertThrows(ResourceNotFoundException.class, () -> appointmentService.registerAppointment(req));
    }

    @Test
    void findByAppointmentNumber_returnsAppointment_whenFound() {
        Appointment appointment = new Appointment();
        appointment.setAppointmentNumber("APT1001");
        when(appointmentDao.findByAppointmentNumber("APT1001")).thenReturn(Optional.of(appointment));

        Appointment result = appointmentService.findByAppointmentNumber("APT1001");

        assertEquals("APT1001", result.getAppointmentNumber());
    }

    @Test
    void findByAppointmentNumber_throwsResourceNotFound_whenMissing() {
        when(appointmentDao.findByAppointmentNumber("APT9999")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> appointmentService.findByAppointmentNumber("APT9999"));
    }
}
