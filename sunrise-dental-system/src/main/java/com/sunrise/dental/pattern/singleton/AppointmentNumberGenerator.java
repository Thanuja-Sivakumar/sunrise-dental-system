package com.sunrise.dental.pattern.singleton;

import java.util.concurrent.atomic.AtomicLong;

/**
 * SINGLETON DESIGN PATTERN
 * ------------------------
 * Only one instance of this generator must ever exist across the whole
 * application, because it is the single source of truth for the
 * next available appointment number. If two instances existed, two
 * different clinic receptionists could generate the same appointment
 * number for two different patients, which would silently corrupt data
 * (duplicate primary business key).
 *
 * Implementation notes:
 *  - The private static instance is created eagerly and is final, so it is
 *    inherently thread-safe without needing synchronized blocks
 *    ("initialization-on-demand" is not required here because eager
 *    initialisation has no expensive side effects).
 *  - AtomicLong is used (rather than a plain long) so concurrent requests
 *    from multiple staff members hitting the REST API at the same time
 *    cannot receive duplicate numbers (no race condition).
 *  - The private constructor prevents any other class from instantiating
 *    this class with `new`.
 */
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
