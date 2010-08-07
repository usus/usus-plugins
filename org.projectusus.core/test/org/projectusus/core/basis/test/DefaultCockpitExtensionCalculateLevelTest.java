package org.projectusus.core.basis.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.projectusus.core.statistics.DefaultCockpitExtension;

public class DefaultCockpitExtensionCalculateLevelTest {

    @Test
    public void avoidDivisionByZero() {
        assertEquals( 0.0, DefaultCockpitExtension.calculateLevel( 1, 0 ), 0.0 );
    }

    @Test
    public void testComputeReverseIndicator() {
        assertEquals( 0, DefaultCockpitExtension.calculateLevel( 0, 0 ), 0.0 );
        assertEquals( 100, DefaultCockpitExtension.calculateLevel( 0, 10 ), 0.0 );
        assertEquals( 0, DefaultCockpitExtension.calculateLevel( 10, 10 ), 0.0 );
        assertEquals( 90, DefaultCockpitExtension.calculateLevel( 10, 100 ), 0.0 );
        assertEquals( 10, DefaultCockpitExtension.calculateLevel( 9, 10 ), 0.0 );
        assertEquals( 0, DefaultCockpitExtension.calculateLevel( 1000, 100 ), 0.0 );
        assertEquals( 100, DefaultCockpitExtension.calculateLevel( 0, 100 ), 0.0 );
        assertEquals( 75, DefaultCockpitExtension.calculateLevel( 1, 4 ), 0.0 );
    }
}
