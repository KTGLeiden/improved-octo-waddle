package com.example.airport;


import com.example.airport.models.Airplane;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AirplaneTests {
    @Test
    public void testDefaultTonsOfFuel() {
        // Setup
        Airplane airplane = new Airplane();

        // Assert
        assertEquals(5, airplane.getTonsOfFuel());
    }

    @Test
    public void brokenTest() {
        // Assert something ridiculous
        assertTrue(false);
    }
}
