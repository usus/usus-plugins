package org.projectusus.core.basis;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CodeProportionsRatioTest {

    @Test
    public void avoidDivisionByZero() {

        assertEquals( 0, CodeProportionsRatio.computeInverse( 1, new CodeStatistic( null, 0 ) ), 0.0 );
    }

    @Test
    public void testComputeReverseIndicator() {
        assertEquals( 0, CodeProportionsRatio.computeInverse( 0, new CodeStatistic( null, 0 ) ), 0.0 );
        assertEquals( 100, CodeProportionsRatio.computeInverse( 0, new CodeStatistic( null, 10 ) ), 0.0 );
        assertEquals( 0, CodeProportionsRatio.computeInverse( 10, new CodeStatistic( null, 10 ) ), 0.0 );
        assertEquals( 90, CodeProportionsRatio.computeInverse( 10, new CodeStatistic( null, 100 ) ), 0.0 );
        assertEquals( 10, CodeProportionsRatio.computeInverse( 9, new CodeStatistic( null, 10 ) ), 0.0 );
        assertEquals( 0, CodeProportionsRatio.computeInverse( 1000, new CodeStatistic( null, 100 ) ), 0.0 );
        assertEquals( 100, CodeProportionsRatio.computeInverse( 0, new CodeStatistic( null, 100 ) ), 0.0 );
        assertEquals( 75, CodeProportionsRatio.computeInverse( 1, new CodeStatistic( null, 4 ) ), 0.0 );
    }
}
