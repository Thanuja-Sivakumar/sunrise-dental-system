package com.sunrise.dental.controller;

import com.sunrise.dental.dto.AppointmentResponse;
import com.sunrise.dental.service.ReportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/daily-schedule")
    public List<AppointmentResponse> dailySchedule(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return reportService.dailySchedule(date).stream()
                .map(AppointmentResponse::from)
                .collect(Collectors.toList());
    }

    @GetMapping("/daily-revenue")
    public Map<String, Object> dailyRevenue(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return reportService.dailyRevenueSummary(date);
    }
}
