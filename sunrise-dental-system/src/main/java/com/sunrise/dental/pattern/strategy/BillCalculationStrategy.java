package com.sunrise.dental.pattern.strategy;

import com.sunrise.dental.model.Appointment;
import java.math.BigDecimal;

/**
 * STRATEGY DESIGN PATTERN
 * ------------------------
 * Encapsulates the varying algorithm for calculating a patient's total bill.
 * Different categories of appointment are billed differently (e.g. an
 * emergency treatment attracts a surcharge on top of the standard
 * treatment cost + consultation fee). New billing rules (e.g. insurance
 * discounts, loyalty discounts) can be added as new strategy
 * implementations without modifying BillingService or existing strategies
 * (Open/Closed Principle).
 */
public interface BillCalculationStrategy {
    BigDecimal calculateTotal(Appointment appointment);
    boolean supports(Appointment appointment);
}
