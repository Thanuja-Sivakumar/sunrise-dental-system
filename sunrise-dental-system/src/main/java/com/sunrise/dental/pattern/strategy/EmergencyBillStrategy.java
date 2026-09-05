package com.sunrise.dental.pattern.strategy;

import com.sunrise.dental.model.Appointment;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class EmergencyBillStrategy implements BillCalculationStrategy {

    private static final BigDecimal SURCHARGE_RATE = new BigDecimal("0.25");

    @Override
    public BigDecimal calculateTotal(Appointment appointment) {
        BigDecimal treatmentCost = appointment.getTreatmentType().getCost();
        BigDecimal consultationFee = appointment.getTreatmentType().getConsultationFee();
        BigDecimal surcharge = treatmentCost.multiply(SURCHARGE_RATE);
        return treatmentCost.add(consultationFee).add(surcharge);
    }

    @Override
    public boolean supports(Appointment appointment) {
        return appointment.isEmergency();
    }
}
