package com.sunrise.dental.pattern;

import com.sunrise.dental.pattern.singleton.AppointmentNumberGenerator;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TEST RATIONALE (see docs/TestPlan.md for the full plan):
 * The AppointmentNumberGenerator is the single most critical piece of logic
 * for data integrity - if it ever produced a duplicate number, two different
 * patients could be merged under one appointment record. These tests were
 * written BEFORE the surrounding service code was wired in (test-driven
 * development), driving the decision to use AtomicLong + a private
 * constructor.
 */
class AppointmentNumberGeneratorTest {

    @Test
    void getInstance_alwaysReturnsTheSameSingletonObject() {
        AppointmentNumberGenerator first = AppointmentNumberGenerator.getInstance();
        AppointmentNumberGenerator second = AppointmentNumberGenerator.getInstance();

        assertSame(first, second, "Singleton must always return the same instance");
    }

    @Test
    void nextAppointmentNumber_hasExpectedFormat() {
        String number = AppointmentNumberGenerator.getInstance().nextAppointmentNumber();
        assertTrue(number.matches("^APT\\d+$"), "Appointment number should look like APT1234");
    }

    @Test
    void nextAppointmentNumber_neverRepeatsUnderConcurrentAccess() throws InterruptedException {
        int threadCount = 50;
        int callsPerThread = 20;
        Set<String> generatedNumbers = ConcurrentHashMap.newKeySet();
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    for (int j = 0; j < callsPerThread; j++) {
                        generatedNumbers.add(AppointmentNumberGenerator.getInstance().nextAppointmentNumber());
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();

        assertEquals(threadCount * callsPerThread, generatedNumbers.size(),
                "Every generated appointment number must be unique, even under concurrent load");
    }
}
