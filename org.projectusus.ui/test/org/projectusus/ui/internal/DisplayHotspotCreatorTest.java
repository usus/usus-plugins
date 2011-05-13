package org.projectusus.ui.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.basis.FileHotspot;
import org.projectusus.core.basis.Hotspot;
import org.projectusus.core.basis.SourceCodeLocation;

public class DisplayHotspotCreatorTest {

    private List<Hotspot> oldHotspots;
    private List<Hotspot> currentHotspots;
    private final SourceCodeLocation location1 = new SourceCodeLocation( "x", 1, 1 ); //$NON-NLS-1$
    private final SourceCodeLocation location2 = new SourceCodeLocation( "y", 2, 2 ); //$NON-NLS-1$
    private final SourceCodeLocation location3 = new SourceCodeLocation( "z", 3, 3 ); //$NON-NLS-1$
    private final IFile file1 = mock( IFile.class );
    private final Hotspot hotspot1_1_1 = new FileHotspot( location1, 1, file1 );
    private final Hotspot hotspot1_2_1 = new FileHotspot( location1, 2, file1 );
    private final Hotspot hotspot2_1_1 = new FileHotspot( location2, 1, file1 );
    private final Hotspot hotspot2_2_1 = new FileHotspot( location2, 2, file1 );
    private final Hotspot hotspot3_1_1 = new FileHotspot( location3, 1, file1 );

    @Before
    public void setup() {
        oldHotspots = new ArrayList<Hotspot>();
        currentHotspots = new ArrayList<Hotspot>();
    }

    @Test
    public void emptyLists() {
        List<DisplayHotspot<?>> result = createDisplay( oldHotspots, currentHotspots );

        assertEquals( 0, result.size() );
    }

    @Test
    public void oneOld() {
        oldHotspots.add( hotspot1_2_1 );
        List<DisplayHotspot<?>> result = createDisplay( oldHotspots, currentHotspots );

        assertEquals( 1, result.size() );
        checkContains( result, hotspot1_2_1, null );
        checkTrendPositive( 2, result.get( 0 ) );
    }

    @Test
    public void oneNew() {
        currentHotspots.add( hotspot1_2_1 );
        List<DisplayHotspot<?>> result = createDisplay( oldHotspots, currentHotspots );

        assertEquals( 1, result.size() );
        checkContains( result, null, hotspot1_2_1 );
        checkTrendNegative( -2, result.get( 0 ) );
    }

    @Test
    public void oldAndNewIdentical() {
        oldHotspots.add( hotspot1_1_1 );
        currentHotspots.add( hotspot1_1_1 );
        List<DisplayHotspot<?>> result = createDisplay( oldHotspots, currentHotspots );

        assertEquals( 1, result.size() );
        checkContains( result, hotspot1_1_1, hotspot1_1_1 );
        checkTrendConstant( result.get( 0 ) );
    }

    @Test
    public void oldBetterThanNew() {
        oldHotspots.add( hotspot1_1_1 );
        currentHotspots.add( hotspot1_2_1 );
        List<DisplayHotspot<?>> result = createDisplay( oldHotspots, currentHotspots );

        assertEquals( 1, result.size() );
        checkContains( result, hotspot1_1_1, hotspot1_2_1 );
        checkTrendNegative( -1, result.get( 0 ) );
    }

    @Test
    public void oldWorseThanNew() {
        oldHotspots.add( hotspot1_2_1 );
        currentHotspots.add( hotspot1_1_1 );
        List<DisplayHotspot<?>> result = createDisplay( oldHotspots, currentHotspots );

        assertEquals( 1, result.size() );
        checkContains( result, hotspot1_2_1, hotspot1_1_1 );
        checkTrendPositive( 1, result.get( 0 ) );
    }

    @Test
    public void oldAndNewDifferentLocations() {
        oldHotspots.add( hotspot1_1_1 );
        currentHotspots.add( hotspot2_1_1 );
        List<DisplayHotspot<?>> result = createDisplay( oldHotspots, currentHotspots );

        assertEquals( 2, result.size() );
        checkContains( result, hotspot1_1_1, null );
        checkContains( result, null, hotspot2_1_1 );
        checkTrendNegative( -1, result.get( 0 ) );
        checkTrendPositive( 1, result.get( 1 ) );
    }

    @Test
    public void oldAndNewOneDifferentOneSame() {
        oldHotspots.add( hotspot1_1_1 );
        oldHotspots.add( hotspot2_1_1 );
        currentHotspots.add( hotspot2_1_1 );
        currentHotspots.add( hotspot3_1_1 );
        List<DisplayHotspot<?>> result = createDisplay( oldHotspots, currentHotspots );

        assertEquals( 3, result.size() );
        checkContains( result, hotspot1_1_1, null );
        checkContains( result, hotspot2_1_1, hotspot2_1_1 );
        checkContains( result, null, hotspot3_1_1 );
        checkTrendNegative( -1, result.get( 0 ) );
        checkTrendConstant( result.get( 1 ) );
        checkTrendPositive( 1, result.get( 2 ) );
    }

    @Test
    public void oldAndNewOneDifferentOneDifferentValue() {
        oldHotspots.add( hotspot1_1_1 );
        oldHotspots.add( hotspot2_1_1 );
        currentHotspots.add( hotspot2_2_1 );
        currentHotspots.add( hotspot3_1_1 );
        List<DisplayHotspot<?>> result = createDisplay( oldHotspots, currentHotspots );

        assertEquals( 3, result.size() );
        checkContains( result, hotspot1_1_1, null );
        checkContains( result, hotspot2_1_1, hotspot2_2_1 );
        checkContains( result, null, hotspot3_1_1 );
        checkTrendNegative( -1, result.get( 0 ) );
        checkTrendNegative( -1, result.get( 1 ) );
        checkTrendPositive( 1, result.get( 2 ) );
    }

    @Test
    public void oldAndNewOneDifferentTwoSame() {
        oldHotspots.add( hotspot1_1_1 );
        oldHotspots.add( hotspot2_1_1 );
        oldHotspots.add( hotspot3_1_1 );
        currentHotspots.add( hotspot1_1_1 );
        currentHotspots.add( hotspot2_2_1 );
        currentHotspots.add( hotspot3_1_1 );
        List<DisplayHotspot<?>> result = createDisplay( oldHotspots, currentHotspots );

        assertEquals( 3, result.size() );
        checkContains( result, hotspot1_1_1, hotspot1_1_1 );
        checkContains( result, hotspot2_1_1, hotspot2_2_1 );
        checkContains( result, hotspot3_1_1, hotspot3_1_1 );
        checkTrendNegative( -1, result.get( 0 ) );
        checkTrendConstant( result.get( 1 ) );
        checkTrendConstant( result.get( 2 ) );
    }

    private void checkContains( List<DisplayHotspot<?>> result, Hotspot oldHotspot, Hotspot newHotspot ) {
        for( DisplayHotspot<?> displayHotspot : result ) {
            if( compare( oldHotspot, displayHotspot.getOldHotspot() ) ) {
                if( compare( newHotspot, displayHotspot.getHotspot() ) ) {
                    if( compareMetricsValue( newHotspot, displayHotspot ) ) {
                        return; // we found a matching entry
                    }
                }
            }
        }
        fail( "List does not contain any elements matching the conditions." ); //$NON-NLS-1$
    }

    private boolean compareMetricsValue( Hotspot newHotspot, DisplayHotspot<?> displayHotspot ) {
        return (newHotspot == null && displayHotspot.getMetricsValue() == 0) || (newHotspot != null && newHotspot.getMetricsValue() == displayHotspot.getMetricsValue());
    }

    private boolean compare( Hotspot expected, Hotspot actual ) {
        return (expected == null && actual == null) || (expected != null && expected.equals( actual ));
    }

    private List<DisplayHotspot<?>> createDisplay( List<Hotspot> oldHots, List<Hotspot> currentHots ) {
        return new DisplayHotspotCreator( oldHots, currentHots ).hotspots();
    }

    private void checkTrendPositive( int expected, DisplayHotspot<?> displayHotspot ) {
        assertTrue( "expected positive trend ", displayHotspot.getTrend() > 0 ); //$NON-NLS-1$
        assertEquals( expected, displayHotspot.getTrend() );
    }

    private void checkTrendNegative( int expected, DisplayHotspot<?> displayHotspot ) {
        assertTrue( "expected negative trend ", displayHotspot.getTrend() < 0 ); //$NON-NLS-1$
        assertEquals( expected, displayHotspot.getTrend() );
    }

    private void checkTrendConstant( DisplayHotspot<?> displayHotspot ) {
        assertTrue( "expected constant trend ", displayHotspot.getTrend() == 0 ); //$NON-NLS-1$
    }

}
