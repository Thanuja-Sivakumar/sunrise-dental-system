package com.sunrise.dental.service;

import com.sunrise.dental.model.Appointment;
import com.sunrise.dental.repository.AppointmentRepository;
import com.sunrise.dental.repository.BillRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * "Come up with a suitable set of reports which you think add more value to
 * your system" - this service supplies:
 *   1. Daily appointment schedule (helps receptionists plan the day)
 *   2. Daily revenue summary (helps clinic management track income)
 */
@Service
public class ReportService {

    private final AppointmentRepository appointmentRepository;
    private final BillRepository billRepository;

    public ReportService(AppointmentRepository appointmentRepository, BillRepository billRepository) {
        this.appointmentRepository = appointmentRepository;
        this.billRepository = billRepository;
    }

    public List<Appointment> dailySchedule(LocalDate date) {
        return appointmentRepository.findByAppointmentDate(date);
    }

    public Map<String, Object> dailyRevenueSummary(LocalDate date) {
        List<Appointment> appointments = appointmentRepository.findByAppointmentDate(date);

        BigDecimal totalRevenue = appointments.stream()
                .map(a -> billRepository.findByAppointment_AppointmentNumber(a.getAppointmentNumber())
                        .map(b -> b.getTotalAmount())
                        .orElse(BigDecimal.ZERO))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return Map.of(
                "date", date,
                "totalAppointments", appointments.size(),
                "totalRevenue", totalRevenue
        );
    }
}
