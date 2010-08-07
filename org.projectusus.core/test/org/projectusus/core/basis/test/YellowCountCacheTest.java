package org.projectusus.core.basis.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.basis.YellowCountCache;
import org.projectusus.core.testutil.ReflectionUtil;

public class YellowCountCacheTest {

    private IProject project1;
    private IProject project2;
    private IMarker[] markers;
    private YellowCountCache cache;

    @Before
    public void setup() throws Exception {
        cache = YellowCountCache.yellowCountCache();
        ReflectionUtil.setValue( cache, new HashMap<IProject, Integer>(), "projectCounts" ); //$NON-NLS-1$
        project1 = mock( IProject.class );
        project2 = mock( IProject.class );
    }

    @Test
    public void noMarkersInOneProject() throws Exception {
        markers = new IMarker[0];
        when( project1.findMarkers( anyString(), anyBoolean(), anyInt() ) ).thenReturn( markers );
        cache.add( project1 );

        assertEquals( 1, cache.projects() );
        assertEquals( 0, cache.yellowProjects() );
        assertEquals( 0, cache.yellows() );
    }

    @Test
    public void noMarkersInTwoProjects() throws Exception {
        markers = new IMarker[0];
        when( project1.findMarkers( anyString(), anyBoolean(), anyInt() ) ).thenReturn( markers );
        when( project2.findMarkers( anyString(), anyBoolean(), anyInt() ) ).thenReturn( markers );
        cache.add( project1 );
        cache.add( project2 );

        assertEquals( 2, cache.projects() );
        assertEquals( 0, cache.yellowProjects() );
        assertEquals( 0, cache.yellows() );
    }

    @Test
    public void noMarkersInTwoProjectsOneIsRemoved() throws Exception {
        markers = new IMarker[0];
        when( project1.findMarkers( anyString(), anyBoolean(), anyInt() ) ).thenReturn( markers );
        when( project2.findMarkers( anyString(), anyBoolean(), anyInt() ) ).thenReturn( markers );
        cache.add( project1 );
        cache.add( project2 );
        cache.clear( project1 );

        assertEquals( 1, cache.projects() );
        assertEquals( 0, cache.yellowProjects() );
        assertEquals( 0, cache.yellows() );
    }

    @Test
    public void oneWarningInOneProject() throws Exception {
        markers = new IMarker[1];
        markers[0] = mock( IMarker.class );
        when( markers[0].getAttribute( IMarker.SEVERITY ) ).thenReturn( new Integer( IMarker.SEVERITY_WARNING ) );
        when( project1.findMarkers( anyString(), anyBoolean(), anyInt() ) ).thenReturn( markers );
        cache.add( project1 );

        assertEquals( 1, cache.projects() );
        assertEquals( 1, cache.yellowProjects() );
        assertEquals( 1, cache.yellows() );
    }

    @Test
    public void twoWarningsInOneProject() throws Exception {
        markers = new IMarker[3];
        markers[0] = mock( IMarker.class );
        markers[1] = mock( IMarker.class );
        markers[2] = mock( IMarker.class );
        when( markers[0].getAttribute( IMarker.SEVERITY ) ).thenReturn( new Integer( IMarker.SEVERITY_WARNING ) );
        when( markers[1].getAttribute( IMarker.SEVERITY ) ).thenReturn( new Integer( IMarker.SEVERITY_WARNING ) );
        when( markers[2].getAttribute( IMarker.SEVERITY ) ).thenReturn( new Integer( IMarker.SEVERITY_ERROR ) );
        when( project1.findMarkers( anyString(), anyBoolean(), anyInt() ) ).thenReturn( markers );
        cache.add( project1 );

        assertEquals( 1, cache.projects() );
        assertEquals( 1, cache.yellowProjects() );
        assertEquals( 2, cache.yellows() );
    }

    @Test
    public void oneWarningInOneOfTwoProjects() throws Exception {
        markers = new IMarker[1];
        markers[0] = mock( IMarker.class );
        when( markers[0].getAttribute( IMarker.SEVERITY ) ).thenReturn( new Integer( IMarker.SEVERITY_WARNING ) );
        when( project1.findMarkers( anyString(), anyBoolean(), anyInt() ) ).thenReturn( markers );
        when( project2.findMarkers( anyString(), anyBoolean(), anyInt() ) ).thenReturn( new IMarker[0] );
        cache.add( project1 );
        cache.add( project2 );

        assertEquals( 2, cache.projects() );
        assertEquals( 1, cache.yellowProjects() );
        assertEquals( 1, cache.yellows() );
    }

}
