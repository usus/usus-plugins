package org.projectusus.core.statistics;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HistogramTest {

    @Test
    public void incrementOnce() {
        Histogram histogram = new Histogram();
        histogram.increment( 1 );
        assertEquals( 1, histogram.countOf( 1 ) );
    }

    @Test
    public void incrementTwice() {
        Histogram histogram = new Histogram();
        histogram.increment( 1 );
        histogram.increment( 1 );
        assertEquals( 2, histogram.countOf( 1 ) );
    }

    @Test
    public void incrementTwoDifferentNumbers() {
        Histogram histogram = new Histogram();
        histogram.increment( 1 );
        histogram.increment( 2 );
        assertEquals( 1, histogram.countOf( 1 ) );
        assertEquals( 1, histogram.countOf( 2 ) );
    }

    @Test
    public void collectAllValues() {
        Histogram histogram = new Histogram();
        histogram.increment( 1 );
        histogram.increment( 3 );
        histogram.increment( 3 );
        assertArrayEquals( new double[] { 1, 3 }, histogram.allNumbers(), 0.000001 );
        assertArrayEquals( new double[] { 1, 2 }, histogram.allValues(), 0.000001 );
    }
}
