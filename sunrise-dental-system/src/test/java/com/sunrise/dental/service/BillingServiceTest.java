package com.sunrise.dental.service;

import com.sunrise.dental.exception.ResourceNotFoundException;
import com.sunrise.dental.model.Appointment;
import com.sunrise.dental.model.Bill;
import com.sunrise.dental.model.Dentist;
import com.sunrise.dental.model.TreatmentType;
import com.sunrise.dental.pattern.factory.BillFactory;
import com.sunrise.dental.pattern.strategy.EmergencyBillStrategy;
import com.sunrise.dental.pattern.strategy.StandardBillStrategy;
import com.sunrise.dental.repository.BillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Covers Task 4 (Calculate and Print Bill), including the Strategy pattern's
 * two billing rules (standard vs. emergency surcharge) via the real
 * BillFactory wired with real strategy objects, so the arithmetic itself is
 * genuinely exercised rather than mocked away.
 */
@ExtendWith(MockitoExtension.class)
class BillingServiceTest {

    @Mock private AppointmentService appointmentService;
    @Mock private BillRepository billRepository;

    private BillingService billingService;
    private BillFactory billFactory;

    private Appointment standardAppointment;
    private Appointment emergencyAppointment;

    @BeforeEach
    void setUp() {
        billFactory = new BillFactory(List.of(new StandardBillStrategy(), new EmergencyBillStrategy()));
        billingService = new BillingService(appointmentService, billFactory, billRepository);

        Dentist dentist = new Dentist("Dr. Ishara Fernando", "Orthodontics");
        TreatmentType treatment = new TreatmentType("Tooth Extraction", new BigDecimal("5000.00"), new BigDecimal("1000.00"));

        standardAppointment = new Appointment();
        standardAppointment.setAppointmentNumber("APT2001");
        standardAppointment.setPatientName("Amaya Rathnayake");
        standardAppointment.setDentist(dentist);
        standardAppointment.setTreatmentType(treatment);
        standardAppointment.setEmergency(false);

        emergencyAppointment = new Appointment();
        emergencyAppointment.setAppointmentNumber("APT2002");
        emergencyAppointment.setPatientName("Sahan Wickramasinghe");
        emergencyAppointment.setDentist(dentist);
        emergencyAppointment.setTreatmentType(treatment);
        emergencyAppointment.setEmergency(true);
    }

    @Test
    void generateBill_standardAppointment_totalsCostPlusConsultationFee() {
        when(appointmentService.findByAppointmentNumber("APT2001")).thenReturn(standardAppointment);
        when(billRepository.findByAppointment_AppointmentNumber("APT2001")).thenReturn(Optional.empty());
        when(billRepository.save(any(Bill.class))).thenAnswer(inv -> inv.getArgument(0));

        Bill bill = billingService.generateBill("APT2001");

        // 5000.00 (treatment) + 1000.00 (consultation) = 6000.00
        assertEquals(0, new BigDecimal("6000.00").compareTo(bill.getTotalAmount()));
    }

    @Test
    void generateBill_emergencyAppointment_appliesTwentyFivePercentSurcharge() {
        when(appointmentService.findByAppointmentNumber("APT2002")).thenReturn(emergencyAppointment);
        when(billRepository.findByAppointment_AppointmentNumber("APT2002")).thenReturn(Optional.empty());
        when(billRepository.save(any(Bill.class))).thenAnswer(inv -> inv.getArgument(0));

        Bill bill = billingService.generateBill("APT2002");

        // 5000.00 + 1000.00 + (25% of 5000.00 = 1250.00) = 7250.00
        assertEquals(0, new BigDecimal("7250.00").compareTo(bill.getTotalAmount()));
    }

    @Test
    void generateBill_returnsExistingBill_insteadOfDuplicating() {
        Bill existingBill = new Bill(standardAppointment, new BigDecimal("1000.00"),
                new BigDecimal("5000.00"), new BigDecimal("6000.00"));

        when(appointmentService.findByAppointmentNumber("APT2001")).thenReturn(standardAppointment);
        when(billRepository.findByAppointment_AppointmentNumber("APT2001")).thenReturn(Optional.of(existingBill));

        Bill result = billingService.generateBill("APT2001");

        assertSame(existingBill, result);
        verify(billRepository, never()).save(any());
    }

    @Test
    void getBill_throwsResourceNotFound_whenNoBillGeneratedYet() {
        when(billRepository.findByAppointment_AppointmentNumber("APT9999")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> billingService.getBill("APT9999"));
    }
}
