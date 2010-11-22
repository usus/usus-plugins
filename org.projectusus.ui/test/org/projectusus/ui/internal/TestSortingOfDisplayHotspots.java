package org.projectusus.ui.internal;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.projectusus.core.basis.FileHotspot;
import org.projectusus.ui.internal.FileDisplayHotspot;

public class TestSortingOfDisplayHotspots {

    @Test
    public void testSortingWorseFirst() {
        sortAndCheck( createHotspotList( //
                3, 3, //
                3, 5, //
                7, 10, //
                5, 5, //
                10, 10 ) );
    }

    @Test
    public void testSortingEqualTrendUsesMetricsValue() {
        sortAndCheck( createHotspotList( //
                3, 3, //
                5, 5, //
                7, 7, //
                5, 5, //
                10, 10 ) );
    }

    @Test
    public void testSortingImprovementsLast() {
        sortAndCheck( createHotspotList( //
                3, 3, //
                3, 5, //
                7, 10, //
                5, 3, //
                7, 5 ) );
    }

    @SuppressWarnings( "nls" )
    public void sortAndCheck( List<FileDisplayHotspot> hotspotList ) {
        Collections.sort( hotspotList );
        int trend = -99;
        int value = 99;
        for( FileDisplayHotspot fileDisplayHotspot : hotspotList ) {
            int newTrend = fileDisplayHotspot.getTrend();
            int newValue = fileDisplayHotspot.getMetricsValue();
            assertTrue( "new Trend: " + newTrend + " of Hotspot: [" + infoFor( fileDisplayHotspot ) + "] is higher then old: " + trend, newTrend >= trend );
            if( newTrend == trend ) {
                assertTrue( "new Value: " + newValue + " of Hotspot: [" + infoFor( fileDisplayHotspot ) + "] is lower then old: " + value, newValue <= value );
            }
            value = newValue;
            trend = newTrend;
        }
    }

    @SuppressWarnings( "nls" )
    private String infoFor( FileDisplayHotspot hotspot ) {
        return "Metrics: " + hotspot.getMetricsValue() + ", Trend: " + hotspot.getTrend();
    }

    private List<FileDisplayHotspot> createHotspotList( int... oldAndNewPairs ) {
        List<FileDisplayHotspot> result = new ArrayList<FileDisplayHotspot>();
        for( int i = 0; i < oldAndNewPairs.length; i = i + 2 ) {
            FileHotspot oldHotspot = new FileHotspot( null, oldAndNewPairs[i], null );
            FileHotspot newHotspot = new FileHotspot( null, oldAndNewPairs[i + 1], null );
            result.add( new FileDisplayHotspot( oldHotspot, newHotspot ) );
        }
        return result;
    }

}
