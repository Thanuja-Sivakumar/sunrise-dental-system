package com.sunrise.dental.dto;

import com.sunrise.dental.model.Bill;
import java.math.BigDecimal;

public class BillResponse {
    private String appointmentNumber;
    private String patientName;
    private BigDecimal consultationFee;
    private BigDecimal treatmentCost;
    private BigDecimal totalAmount;

    public static BillResponse from(Bill bill) {
        BillResponse r = new BillResponse();
        r.appointmentNumber = bill.getAppointment().getAppointmentNumber();
        r.patientName = bill.getAppointment().getPatientName();
        r.consultationFee = bill.getConsultationFee();
        r.treatmentCost = bill.getTreatmentCost();
        r.totalAmount = bill.getTotalAmount();
        return r;
    }

    public String getAppointmentNumber() { return appointmentNumber; }
    public String getPatientName() { return patientName; }
    public BigDecimal getConsultationFee() { return consultationFee; }
    public BigDecimal getTreatmentCost() { return treatmentCost; }
    public BigDecimal getTotalAmount() { return totalAmount; }
}
