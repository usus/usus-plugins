package org.projectusus.core.basis;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CodeProportionsRatioTest {

    private String label = ""; //$NON-NLS-1$

    @Test
    public void avoidDivisionByZero() {

        assertEquals( 0, CodeProportionsRatio.computeInverse( 1, new CodeStatistic( label, 0 ) ), 0.0 );
    }

    @Test
    public void testComputeReverseIndicator() {
        assertEquals( 0, CodeProportionsRatio.computeInverse( 0, new CodeStatistic( label, 0 ) ), 0.0 );
        assertEquals( 100, CodeProportionsRatio.computeInverse( 0, new CodeStatistic( label, 10 ) ), 0.0 );
        assertEquals( 0, CodeProportionsRatio.computeInverse( 10, new CodeStatistic( label, 10 ) ), 0.0 );
        assertEquals( 90, CodeProportionsRatio.computeInverse( 10, new CodeStatistic( label, 100 ) ), 0.0 );
        assertEquals( 10, CodeProportionsRatio.computeInverse( 9, new CodeStatistic( label, 10 ) ), 0.0 );
        assertEquals( 0, CodeProportionsRatio.computeInverse( 1000, new CodeStatistic( label, 100 ) ), 0.0 );
        assertEquals( 100, CodeProportionsRatio.computeInverse( 0, new CodeStatistic( label, 100 ) ), 0.0 );
        assertEquals( 75, CodeProportionsRatio.computeInverse( 1, new CodeStatistic( label, 4 ) ), 0.0 );
    }
}
