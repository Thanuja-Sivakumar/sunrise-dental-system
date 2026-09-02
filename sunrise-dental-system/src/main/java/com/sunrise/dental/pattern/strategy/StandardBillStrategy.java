package com.sunrise.dental.pattern.strategy;

import com.sunrise.dental.model.Appointment;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class StandardBillStrategy implements BillCalculationStrategy {

    @Override
    public BigDecimal calculateTotal(Appointment appointment) {
        BigDecimal treatmentCost = appointment.getTreatmentType().getCost();
        BigDecimal consultationFee = appointment.getTreatmentType().getConsultationFee();
        return treatmentCost.add(consultationFee);
    }

    @Override
    public boolean supports(Appointment appointment) {
        return !appointment.isEmergency();
    }
}
