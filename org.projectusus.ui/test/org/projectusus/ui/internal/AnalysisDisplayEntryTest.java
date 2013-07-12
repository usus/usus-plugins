package org.projectusus.ui.internal;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.basis.FileHotspot;
import org.projectusus.core.basis.Histogram;
import org.projectusus.core.basis.Hotspot;
import org.projectusus.core.basis.SourceCodeLocation;

public class AnalysisDisplayEntryTest {

    @Test
    public void noHistory_noHotspots() {
        AnalysisDisplayEntry entry = new AnalysisDisplayEntry( codeProportion( 0.5 ) );
        assertEquals( 0, entry.getTrend() );
        assertEquals( 0, entry.getAdvancedTrend() );
    }

    @Test
    public void smallChangeInHistory_noHotspots() {
        AnalysisDisplayEntry entry = new AnalysisDisplayEntry( codeProportion( 0.5 ) );
        entry.setCodeProportion( codeProportion( 1.49 ) );
        assertEquals( 0, entry.getTrend() );
        assertEquals( 0, entry.getAdvancedTrend() );
    }

    @Test
    public void largeChangeInHistory_noHotspots() {
        AnalysisDisplayEntry entry = new AnalysisDisplayEntry( codeProportion( 0.5 ) );
        entry.setCodeProportion( codeProportion( 1.5 ) );
        assertEquals( 1, entry.getTrend() );
        assertEquals( 1, entry.getAdvancedTrend() );
    }

    @Test
    public void noHistory_hasEmptyListOfHotspots() {
        AnalysisDisplayEntry entry = new AnalysisDisplayEntry( codeProportion( 0.5, new ArrayList<Hotspot>() ) );
        assertEquals( 0, entry.getTrend() );
        assertEquals( 0, entry.getAdvancedTrend() );
    }

    @Test
    public void noHistory_hasOneHotspot() {
        ArrayList<Hotspot> hotspots = new ArrayList<Hotspot>();
        hotspots.add( hotspot( "A", 5 ) );
        AnalysisDisplayEntry entry = new AnalysisDisplayEntry( codeProportion( 0.5, hotspots ) );
        assertEquals( 0, entry.getTrend() );
        assertEquals( 0, entry.getAdvancedTrend() );
    }

    @Test
    public void largeChangeInHistory_hasOneHotspot_hotspotRemainsConstant() {
        ArrayList<Hotspot> hotspots = new ArrayList<Hotspot>();
        hotspots.add( hotspot( "A", 5 ) );
        AnalysisDisplayEntry entry = new AnalysisDisplayEntry( codeProportion( 0.5, hotspots ) );
        entry.setCodeProportion( codeProportion( 2.5, hotspots ) );
        assertEquals( 0, entry.getTrend() );
        assertEquals( 0, entry.getAdvancedTrend() );
    }

    @Test
    public void hotspotGotWorse() {
        ArrayList<Hotspot> hotspots = new ArrayList<Hotspot>();
        hotspots.add( hotspot( "A", 5 ) );
        AnalysisDisplayEntry entry = new AnalysisDisplayEntry( codeProportion( 0.5, hotspots ) );

        ArrayList<Hotspot> newHotspots = new ArrayList<Hotspot>();
        newHotspots.add( hotspot( "A", 10 ) );
        entry.setCodeProportion( codeProportion( 0.5, newHotspots ) );

        assertEquals( 0, entry.getTrend() );
        assertEquals( 5, entry.getAdvancedTrend() );
    }

    @Test
    public void hotspotGotBetter() {
        ArrayList<Hotspot> hotspots = new ArrayList<Hotspot>();
        hotspots.add( hotspot( "A", 5 ) );
        AnalysisDisplayEntry entry = new AnalysisDisplayEntry( codeProportion( 0.5, hotspots ) );

        ArrayList<Hotspot> newHotspots = new ArrayList<Hotspot>();
        newHotspots.add( hotspot( "A", 1 ) );
        entry.setCodeProportion( codeProportion( 0.5, newHotspots ) );

        assertEquals( 0, entry.getTrend() );
        assertEquals( -4, entry.getAdvancedTrend() );
    }

    @Test
    public void hotspotWasAdded() {
        ArrayList<Hotspot> hotspots = new ArrayList<Hotspot>();
        hotspots.add( hotspot( "A", 5 ) );
        AnalysisDisplayEntry entry = new AnalysisDisplayEntry( codeProportion( 0.5, hotspots ) );

        ArrayList<Hotspot> newHotspots = new ArrayList<Hotspot>();
        newHotspots.add( hotspot( "A", 5 ) );
        newHotspots.add( hotspot( "B", 10 ) );
        entry.setCodeProportion( codeProportion( 0.5, newHotspots ) );

        assertEquals( 1, entry.getTrend() );
        assertEquals( 10, entry.getAdvancedTrend() );
    }

    @Test
    public void hotspotWasRemoved() {
        ArrayList<Hotspot> hotspots = new ArrayList<Hotspot>();
        hotspots.add( hotspot( "A", 5 ) );
        hotspots.add( hotspot( "B", 5 ) );
        AnalysisDisplayEntry entry = new AnalysisDisplayEntry( codeProportion( 0.5, hotspots ) );

        ArrayList<Hotspot> newHotspots = new ArrayList<Hotspot>();
        newHotspots.add( hotspot( "A", 5 ) );
        entry.setCodeProportion( codeProportion( 0.5, newHotspots ) );

        assertEquals( 0, entry.getTrend() ); // TODO ?!?!
        assertEquals( -5, entry.getAdvancedTrend() );
    }

    private static CodeProportion codeProportion( double average ) {
        return new CodeProportion( null, null, 0, null, average );
    }

    private static CodeProportion codeProportion( double average, ArrayList<Hotspot> hotspots ) {
        return new CodeProportion( null, null, null, 0, null, average, hotspots, new Histogram(), null );
    }

    private static FileHotspot hotspot( String name, int metricsValue ) {
        return new FileHotspot( new SourceCodeLocation( name, 0, 0 ), metricsValue, null );
    }
}
