package com.sunrise.dental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Sunrise Dental Clinic - Online Appointment Reservation & Billing System

 Run (or
 * `mvn spring-boot:run`) to start the embedded web server on
 * http://localhost:8080
 */
@SpringBootApplication
public class DentalApplication {
    public static void main(String[] args) {
        SpringApplication.run(DentalApplication.class, args);
    }
}
