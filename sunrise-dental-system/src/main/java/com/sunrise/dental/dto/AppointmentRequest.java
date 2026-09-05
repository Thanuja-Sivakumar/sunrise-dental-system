package com.sunrise.dental.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;


public class AppointmentRequest {

    @NotBlank(message = "Patient name is required")
    @Size(max = 100)
    private String patientName;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Contact number is required")
    @Pattern(regexp = "^[0-9+\\-() ]{7,15}$", message = "Contact number format is invalid")
    private String contactNumber;

    @NotNull(message = "Dentist must be selected")
    private Long dentistId;

    @NotNull(message = "Treatment type must be selected")
    private Long treatmentTypeId;

    @NotNull(message = "Appointment date is required")
    @FutureOrPresent(message = "Appointment date cannot be in the past")
    private LocalDate appointmentDate;

    @NotNull(message = "Appointment time is required")
    private LocalTime appointmentTime;

    private boolean emergency = false;

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    public Long getDentistId() { return dentistId; }
    public void setDentistId(Long dentistId) { this.dentistId = dentistId; }
    public Long getTreatmentTypeId() { return treatmentTypeId; }
    public void setTreatmentTypeId(Long treatmentTypeId) { this.treatmentTypeId = treatmentTypeId; }
    public LocalDate getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDate appointmentDate) { this.appointmentDate = appointmentDate; }
    public LocalTime getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(LocalTime appointmentTime) { this.appointmentTime = appointmentTime; }
    public boolean isEmergency() { return emergency; }
    public void setEmergency(boolean emergency) { this.emergency = emergency; }
}
