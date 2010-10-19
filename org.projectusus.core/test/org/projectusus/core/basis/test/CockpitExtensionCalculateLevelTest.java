package org.projectusus.core.basis.test;

import static org.junit.Assert.assertEquals;
import static org.projectusus.core.statistics.CockpitExtension.calculateAverage;

import org.junit.Test;

public class CockpitExtensionCalculateLevelTest {

    private static final double DELTA = 0.0;

    @Test
    public void avoidDivisionByZero() {
        assertEquals( 0, calculateAverage( 1, 0 ), DELTA );
    }

    @Test
    public void testComputeAverage() {
        assertEquals( 0, calculateAverage( 0, 0 ), DELTA );
        assertEquals( 0, calculateAverage( 0, 10 ), DELTA );
        assertEquals( 100.0, calculateAverage( 10, 10 ), DELTA );
        assertEquals( 10.0, calculateAverage( 10, 100 ), DELTA );
        assertEquals( 90.0, calculateAverage( 9, 10 ), DELTA );
        assertEquals( 1000.0, calculateAverage( 1000, 100 ), DELTA );
        assertEquals( 0, calculateAverage( 0, 100 ), DELTA );
        assertEquals( 25.0, calculateAverage( 1, 4 ), DELTA );
    }
}
