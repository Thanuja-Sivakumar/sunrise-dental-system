package com.sunrise.dental.controller;

import com.sunrise.dental.dto.BillResponse;
import com.sunrise.dental.service.BillingService;
import org.springframework.web.bind.annotation.*;

// Calculate and Print Bill
@RestController
@RequestMapping("/api/bills")
public class BillController {

    private final BillingService billingService;

    public BillController(BillingService billingService) {
        this.billingService = billingService;
    }

    @PostMapping("/{appointmentNumber}/generate")
    public BillResponse generate(@PathVariable String appointmentNumber) {
        return BillResponse.from(billingService.generateBill(appointmentNumber));
    }

    @GetMapping("/{appointmentNumber}")
    public BillResponse get(@PathVariable String appointmentNumber) {
        return BillResponse.from(billingService.getBill(appointmentNumber));
    }
}
