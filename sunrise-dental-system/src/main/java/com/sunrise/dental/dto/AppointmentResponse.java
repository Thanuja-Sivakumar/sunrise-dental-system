package com.sunrise.dental.dto;

import com.sunrise.dental.model.Appointment;
import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentResponse {
    private String appointmentNumber;
    private String patientName;
    private String address;
    private String contactNumber;
    private String dentistName;
    private String treatmentTypeName;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private boolean emergency;

    public static AppointmentResponse from(Appointment a) {
        AppointmentResponse r = new AppointmentResponse();
        r.appointmentNumber = a.getAppointmentNumber();
        r.patientName = a.getPatientName();
        r.address = a.getAddress();
        r.contactNumber = a.getContactNumber();
        r.dentistName = a.getDentist().getName();
        r.treatmentTypeName = a.getTreatmentType().getName();
        r.appointmentDate = a.getAppointmentDate();
        r.appointmentTime = a.getAppointmentTime();
        r.emergency = a.isEmergency();
        return r;
    }

    public String getAppointmentNumber() { return appointmentNumber; }
    public String getPatientName() { return patientName; }
    public String getAddress() { return address; }
    public String getContactNumber() { return contactNumber; }
    public String getDentistName() { return dentistName; }
    public String getTreatmentTypeName() { return treatmentTypeName; }
    public LocalDate getAppointmentDate() { return appointmentDate; }
    public LocalTime getAppointmentTime() { return appointmentTime; }
    public boolean isEmergency() { return emergency; }
}
