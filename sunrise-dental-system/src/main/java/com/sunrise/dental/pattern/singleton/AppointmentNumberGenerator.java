package com.sunrise.dental.pattern.singleton;

import java.util.concurrent.atomic.AtomicLong;


public final class AppointmentNumberGenerator {

    private static final AppointmentNumberGenerator INSTANCE = new AppointmentNumberGenerator();

    private static final String PREFIX = "APT";
    private final AtomicLong counter;

    private AppointmentNumberGenerator() {
        // Start numbering from 1001 so IDs look realistic (APT1001, APT1002, ...)
        this.counter = new AtomicLong(1000);
    }

    public static AppointmentNumberGenerator getInstance() {
        return INSTANCE;
    }

    /**
     * Atomically increments and returns the next unique appointment number.
     */
    public synchronized String nextAppointmentNumber() {
        long next = counter.incrementAndGet();
        return PREFIX + next;
    }
}
