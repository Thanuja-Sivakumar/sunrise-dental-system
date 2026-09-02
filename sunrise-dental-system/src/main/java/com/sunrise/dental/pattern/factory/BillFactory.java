package com.sunrise.dental.pattern.factory;

import com.sunrise.dental.model.Appointment;
import com.sunrise.dental.model.Bill;
import com.sunrise.dental.pattern.strategy.BillCalculationStrategy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * FACTORY DESIGN PATTERN
 * ------------------------
 * Centralises the creation of Bill objects. Client code (BillingService)
 * simply calls factory.createBill(appointment) without needing to know:
 *   1. Which BillCalculationStrategy applies to this appointment
 *      (Standard vs Emergency), or
 *   2. How the Bill entity's fields should be populated/derived.
 *
 * This keeps object-construction logic out of the service layer and makes
 * it trivial to add new Bill "flavours" later (e.g. InsuranceBill) by
 * extending this factory rather than editing every calling class.
 */
@Component
public class BillFactory {

    private final List<BillCalculationStrategy> strategies;

    public BillFactory(List<BillCalculationStrategy> strategies) {
        this.strategies = strategies;
    }

    public Bill createBill(Appointment appointment) {
        BillCalculationStrategy strategy = strategies.stream()
                .filter(s -> s.supports(appointment))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        "No billing strategy available for appointment " + appointment.getAppointmentNumber()));

        var total = strategy.calculateTotal(appointment);
        var treatmentCost = appointment.getTreatmentType().getCost();
        var consultationFee = appointment.getTreatmentType().getConsultationFee();

        return new Bill(appointment, consultationFee, treatmentCost, total);
    }
}
