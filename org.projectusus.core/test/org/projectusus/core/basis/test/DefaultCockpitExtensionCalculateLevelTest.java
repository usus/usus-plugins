package org.projectusus.core.basis.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.projectusus.core.statistics.DefaultCockpitExtension;

public class DefaultCockpitExtensionCalculateLevelTest {

    private static final double DELTA = 0.0;

    @Test
    public void avoidDivisionByZero() {
        assertEquals( 0, DefaultCockpitExtension.calculateAverage( 1, 0 ), DELTA );
    }

    @Test
    public void testComputeAverage() {
        assertEquals( 0, DefaultCockpitExtension.calculateAverage( 0, 0 ), DELTA );
        assertEquals( 0, DefaultCockpitExtension.calculateAverage( 0, 10 ), DELTA );
        assertEquals( 1, DefaultCockpitExtension.calculateAverage( 10, 10 ), DELTA );
        assertEquals( 0.1, DefaultCockpitExtension.calculateAverage( 10, 100 ), DELTA );
        assertEquals( 0.9, DefaultCockpitExtension.calculateAverage( 9, 10 ), DELTA );
        assertEquals( 10, DefaultCockpitExtension.calculateAverage( 1000, 100 ), DELTA );
        assertEquals( 0, DefaultCockpitExtension.calculateAverage( 0, 100 ), DELTA );
        assertEquals( .25, DefaultCockpitExtension.calculateAverage( 1, 4 ), DELTA );
    }
}
