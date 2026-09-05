package com.sunrise.dental.pattern.strategy;

import com.sunrise.dental.model.Appointment;
import java.math.BigDecimal;


public interface BillCalculationStrategy {
    BigDecimal calculateTotal(Appointment appointment);
    boolean supports(Appointment appointment);
}
