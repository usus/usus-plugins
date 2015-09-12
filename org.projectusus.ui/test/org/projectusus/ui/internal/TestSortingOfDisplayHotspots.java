package org.projectusus.ui.internal;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.projectusus.core.basis.FileHotspot;
import org.projectusus.core.basis.SourceCodeLocation;

public class TestSortingOfDisplayHotspots {

    @Test
    public void trendForConstantHotspot() {
        List<FileDisplayHotspot> hotspots = createHotspotList( 3, 3 );
        assertEquals( 0, hotspots.get( 0 ).getTrend() );
    }

    @Test
    public void trendForImprovedHotspot() {
        List<FileDisplayHotspot> hotspots = createHotspotList( 6, 3 );
        assertEquals( -3, hotspots.get( 0 ).getTrend() );
    }

    @Test
    public void trendForWorsenedHotspot() {
        List<FileDisplayHotspot> hotspots = createHotspotList( 3, 7 );
        assertEquals( 4, hotspots.get( 0 ).getTrend() );
    }

    @Test
    public void twoUnchangedHotspots_largerValueIsFirst() {
        List<FileDisplayHotspot> hotspots = createHotspotList( 3, 3, 5, 5 );
        Collections.sort( hotspots );
        assertEquals( 5, hotspots.get( 0 ).currentMetricsValue() );
        assertEquals( 3, hotspots.get( 1 ).currentMetricsValue() );

        // trends are both 0
        assertEquals( 0, hotspots.get( 0 ).getTrend() );
        assertEquals( 0, hotspots.get( 1 ).getTrend() );
    }

    @Test
    public void twoWorsenedHotspots_largerValueIsFirst() {
        List<FileDisplayHotspot> hotspots = createHotspotList( 1, 4, 5, 6 );
        Collections.sort( hotspots );
        assertEquals( 6, hotspots.get( 0 ).currentMetricsValue() );
        assertEquals( 4, hotspots.get( 1 ).currentMetricsValue() );

        // although the smaller trend comes first:
        assertEquals( 1, hotspots.get( 0 ).getTrend() );
        assertEquals( 3, hotspots.get( 1 ).getTrend() );
    }

    @Test
    public void twoImprovedHotspots_largerValueIsFirst() {
        List<FileDisplayHotspot> hotspots = createHotspotList( 3, 2, 9, 4 );
        Collections.sort( hotspots );
        assertEquals( 4, hotspots.get( 0 ).currentMetricsValue() );
        assertEquals( 2, hotspots.get( 1 ).currentMetricsValue() );

        // although the smaller trend comes first:
        assertEquals( -5, hotspots.get( 0 ).getTrend() );
        assertEquals( -1, hotspots.get( 1 ).getTrend() );
    }

    @Test
    public void improvedAndWorsenedHotspots_largerValueIsFirst() {
        List<FileDisplayHotspot> hotspots = createHotspotList( 3, 2, 2, 6 );
        Collections.sort( hotspots );
        assertEquals( 6, hotspots.get( 0 ).currentMetricsValue() );
        assertEquals( 2, hotspots.get( 1 ).currentMetricsValue() );

        assertEquals( 4, hotspots.get( 0 ).getTrend() );
        assertEquals( -1, hotspots.get( 1 ).getTrend() );
    }

    @Test
    public void worsenedAndImprovedHotspots_largerValueIsFirst() {
        List<FileDisplayHotspot> hotspots = createHotspotList( 3, 4, 6, 5 );
        Collections.sort( hotspots );
        assertEquals( 5, hotspots.get( 0 ).currentMetricsValue() );
        assertEquals( 4, hotspots.get( 1 ).currentMetricsValue() );

        // although the smaller trend comes first:
        assertEquals( -1, hotspots.get( 0 ).getTrend() );
        assertEquals( 1, hotspots.get( 1 ).getTrend() );
    }

    @Test
    public void twoEqualHotspots_OneImproved_largerTrendIsFirst() {
        List<FileDisplayHotspot> hotspots = createHotspotList( 4, 4, 5, 4 );
        Collections.sort( hotspots );
        assertEquals( 0, hotspots.get( 0 ).getTrend() );
        assertEquals( -1, hotspots.get( 1 ).getTrend() );
    }

    @Test
    public void twoEqualHotspots_OneWorsened_largerTrendIsFirst() {
        List<FileDisplayHotspot> hotspots = createHotspotList( 4, 4, 3, 4 );
        Collections.sort( hotspots );
        assertEquals( 1, hotspots.get( 0 ).getTrend() );
        assertEquals( 0, hotspots.get( 1 ).getTrend() );
    }

    @Test
    public void twoEqualHotspots_OneImprovedOneWorsened_largerTrendIsFirst() {
        List<FileDisplayHotspot> hotspots = createHotspotList( 5, 4, 3, 4 );
        Collections.sort( hotspots );
        assertEquals( 1, hotspots.get( 0 ).getTrend() );
        assertEquals( -1, hotspots.get( 1 ).getTrend() );
    }

    @Test
    public void twoEqualHotspots_SameTrend_smallerNameIsFirst() {
        List<FileDisplayHotspot> hotspots = createHotspotList( 5, 4, 5, 4 );
        Collections.sort( hotspots );
        assertEquals( "hotspot0", hotspots.get( 0 ).getName() );
        assertEquals( "hotspot2", hotspots.get( 1 ).getName() );
    }

    private List<FileDisplayHotspot> createHotspotList( int... oldAndNewPairs ) {
        List<FileDisplayHotspot> result = new ArrayList<FileDisplayHotspot>();
        for( int i = 0; i < oldAndNewPairs.length - 1; i = i + 2 ) {
            SourceCodeLocation location = new SourceCodeLocation( "hotspot" + i, 0, 0 );
            FileHotspot oldHotspot = new FileHotspot( location, oldAndNewPairs[i], null );
            FileHotspot newHotspot = new FileHotspot( location, oldAndNewPairs[i + 1], null );
            result.add( new FileDisplayHotspot( oldHotspot, newHotspot ) );
        }
        return result;
    }

}
