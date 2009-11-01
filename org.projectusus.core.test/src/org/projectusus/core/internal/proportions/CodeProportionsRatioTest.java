package org.projectusus.core.internal.proportions;

import static org.junit.Assert.*;

import org.junit.Test;

public class CodeProportionsRatioTest {

    @Test
    public void testCompute() {
        assertEquals( 100, new CodeProportionsRatio( 10, 10 ).compute(), 0.0 );
        assertEquals( 10, new CodeProportionsRatio( 10, 100 ).compute(), 0.0 );
        assertEquals( 100, new CodeProportionsRatio( 1000, 100 ).compute(), 0.0 );
        assertEquals( 0, new CodeProportionsRatio( 0, 100 ).compute(), 0.0 );
    }

    @Test
    public void avoidDivisionByZero() {
        assertEquals( 0, new CodeProportionsRatio( 1, 0 ).compute(), 0.0 );
    }

    @Test
    public void reverseIndicatorZeroWithZeroCases() {
        assertEquals( 0, new CodeProportionsRatio( 0, 0 ).computeReverseIndicator(), 0.0 );
    }

    @Test
    public void testComputeReverseIndicator() {
        assertEquals( 0, new CodeProportionsRatio( 10, 10 ).computeReverseIndicator(), 0.0 );
        assertEquals( 90, new CodeProportionsRatio( 10, 100 ).computeReverseIndicator(), 0.0 );
    }
}
