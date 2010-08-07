package org.projectusus.defaultmetrics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.projectusus.statistics.ACDStatistic;

public class AcdLimitTest {

    @Test
    public void limitFor1() {
        int classCount = 1;
        int expected = 1;
        int limit = ACDStatistic.calculateCcdLimit( classCount );
        assertEquals( expected, limit );
        assertEquals( 1.0, calcRelativeAcd( expected, classCount ), 0.01 );
    }

    @Test
    public void limitFor2() {
        int classCount = 2;
        int expected = 2;
        int limit = ACDStatistic.calculateCcdLimit( classCount );
        assertEquals( expected, limit );
        assertEquals( 0.5, calcRelativeAcd( expected, classCount ), 0.01 );
    }

    @Test
    public void limitFor3() {
        int classCount = 3;
        int expected = 2;
        int limit = ACDStatistic.calculateCcdLimit( classCount );
        assertEquals( expected, limit );
        assertTrue( 0.25 > calcRelativeAcd( expected, classCount ) );
    }

    @Test
    public void limitFor10() {
        int classCount = 10;
        int expected = 5;
        int limit = ACDStatistic.calculateCcdLimit( classCount );
        assertEquals( expected, limit );
        assertTrue( 0.1 > calcRelativeAcd( expected, classCount ) );
    }

    @Test
    public void limitFor15() {
        int classCount = 15;
        int expected = 7;
        int limit = ACDStatistic.calculateCcdLimit( classCount );
        assertEquals( expected, limit );
        assertTrue( 0.04 > calcRelativeAcd( expected, classCount ) );
    }

    @Test
    public void limitFor50() {
        int classCount = 50;
        int expected = 13;
        int limit = ACDStatistic.calculateCcdLimit( classCount );
        assertEquals( expected, limit );
        assertTrue( 0.04 > calcRelativeAcd( expected, classCount ) );
    }

    @Test
    public void limitFor100() {
        int classCount = 100;
        int expected = 20;
        int limit = ACDStatistic.calculateCcdLimit( classCount );
        assertEquals( expected, limit );
        assertTrue( 0.04 > calcRelativeAcd( expected, classCount ) );
    }

    @Test
    public void limitFor500() {
        int classCount = 500;
        int expected = 51;
        int limit = ACDStatistic.calculateCcdLimit( classCount );
        assertEquals( expected, limit );
        assertTrue( 0.04 > calcRelativeAcd( expected, classCount ) );
    }

    @Test
    public void limitFor5000() {
        int classCount = 5000;
        int expected = 191;
        int limit = ACDStatistic.calculateCcdLimit( classCount );
        assertEquals( expected, limit );
        assertTrue( 0.04 > calcRelativeAcd( expected, classCount ) );
    }

    private double calcRelativeAcd( int ccdLimit, int classCount ) {
        return ((double)ccdLimit) / (classCount * classCount);
    }
}
