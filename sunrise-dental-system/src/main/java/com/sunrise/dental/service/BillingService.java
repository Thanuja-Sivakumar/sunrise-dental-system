package com.sunrise.dental.service;

import com.sunrise.dental.exception.ResourceNotFoundException;
import com.sunrise.dental.model.Appointment;
import com.sunrise.dental.model.Bill;
import com.sunrise.dental.pattern.factory.BillFactory;
import com.sunrise.dental.repository.BillRepository;
import org.springframework.stereotype.Service;

/**
 * Implements Task 4 (Calculate and Print Bill). Delegates the actual
 * calculation to the Factory + Strategy patterns so this service stays
 * focused on orchestration (find appointment -> build bill -> persist ->
 * return), not on billing arithmetic.
 */
@Service
public class BillingService {

    private final AppointmentService appointmentService;
    private final BillFactory billFactory;
    private final BillRepository billRepository;

    public BillingService(AppointmentService appointmentService, BillFactory billFactory, BillRepository billRepository) {
        this.appointmentService = appointmentService;
        this.billFactory = billFactory;
        this.billRepository = billRepository;
    }

    public Bill generateBill(String appointmentNumber) {
        Appointment appointment = appointmentService.findByAppointmentNumber(appointmentNumber);

        // Re-generate the bill (e.g. reprinting a receipt) rather than duplicating rows
        return billRepository.findByAppointment_AppointmentNumber(appointmentNumber)
                .orElseGet(() -> billRepository.save(billFactory.createBill(appointment)));
    }

    public Bill getBill(String appointmentNumber) {
        return billRepository.findByAppointment_AppointmentNumber(appointmentNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No bill has been generated yet for appointment: " + appointmentNumber));
    }
}
