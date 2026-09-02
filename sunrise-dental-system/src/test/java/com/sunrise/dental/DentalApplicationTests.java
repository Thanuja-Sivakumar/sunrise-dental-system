package com.sunrise.dental;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Integration smoke test: confirms the full Spring application context
 * (all controllers, services, repositories, security config, and the
 * embedded H2 database) starts up without errors.
 */
@SpringBootTest
class DentalApplicationTests {

    @Test
    void contextLoads() {
        // If the ApplicationContext fails to start, this test fails automatically.
    }
}
