// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.rawdata.collectors;

import static org.eclipse.core.resources.IMarker.BOOKMARK;
import static org.eclipse.core.resources.IMarker.LINE_NUMBER;
import static org.eclipse.core.resources.IMarker.PROBLEM;
import static org.eclipse.core.resources.IMarker.SEVERITY;
import static org.eclipse.core.resources.IMarker.SEVERITY_ERROR;
import static org.eclipse.core.resources.IMarker.SEVERITY_INFO;
import static org.eclipse.core.resources.IMarker.SEVERITY_WARNING;
import static org.junit.Assert.assertEquals;
import static org.projectusus.core.basis.YellowCountCache.yellowCountCache;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.projectusus.adapter.ForcedRecompute;
import org.projectusus.core.statistics.test.PDETestForMetricsComputation;

public class ProjectYellowCountPDETest extends PDETestForMetricsComputation {

    @Before
    public void setup() throws Exception {
        yellowCountCache().clear();
    }

    @Test
    @Ignore
    // TODO: this test is not stable!
    public void findMarkerOnProject_FindsMarker() throws CoreException {
        project.get().createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        workspace.buildFullyAndWait();
        assertEquals( 1, yellowCountCache().yellows() );
    }

    @Test
    public void findMarkerOnJavaFile_FindsMarker() throws CoreException, InterruptedException {
        IFile file = project.createFile( "a.java", "no content" );
        file.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        workspace.buildFullyAndWait();

        // TODO #57 Seems to be necessary to make Usus aware of the project changes...
        new ForcedRecompute().schedule();
        Thread.sleep( 1000 );

        assertEquals( 1, yellowCountCache().yellows() );
    }

    @Test
    @Ignore
    // TODO: this test is not stable!
    public void findMarkerOnNonJavaFile_DoesNotFindMarker() throws Exception {
        IFile file = project.createFile( "a", "no content" );
        file.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        workspace.buildFullyAndWait();
        assertEquals( 0, yellowCountCache().yellows() );
    }

    @Test
    @Ignore
    // TODO: this test is not stable!
    public void findMultipleMarkersOnJavaFile_FindsMarkers() throws CoreException {
        IFile file = project.createFile( "a.java", "no content\non two lines" );
        IMarker marker1 = file.createMarker( PROBLEM );
        marker1.setAttribute( LINE_NUMBER, 1 );
        marker1.setAttribute( SEVERITY, SEVERITY_WARNING );
        IMarker marker2 = file.createMarker( PROBLEM );
        marker2.setAttribute( LINE_NUMBER, 2 );
        marker2.setAttribute( SEVERITY, SEVERITY_WARNING );
        workspace.buildFullyAndWait();
        assertEquals( 2, yellowCountCache().yellows() );
        assertEquals( 1, yellowCountCache().yellowProjects() );
    }

    @Test
    public void findMarkersOnMultipleFiles() throws Exception {
        IFile file1 = project.createFile( "a", "no content" );
        file1.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        IFile file2 = project.createFile( "b.java", "no content" );
        file2.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        workspace.buildFullyAndWait();
        assertEquals( 2, yellowCountCache().yellows() );
    }

    @Test
    @Ignore
    // TODO: this test is not stable!
    public void hotspotsOnMultipleNonJavaFiles_IgnoresHotspots() throws Exception {
        IFile fileA = project.createFile( "a", "no content" );
        fileA.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        IFile fileB = project.createFile( "b", "no content" );
        fileB.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        fileB.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        workspace.buildFullyAndWait();
        assertEquals( 0, yellowCountCache().yellows() );
    }

    @Test
    public void hotspotsOnMultipleJavaFiles_FindsHotspots() throws CoreException {
        IFile fileA = project.createFile( "a.java", "no content" );
        fileA.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        IFile fileB = project.createFile( "b.java", "no content" );
        fileB.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        fileB.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        workspace.buildFullyAndWait();
        assertEquals( 3, yellowCountCache().yellows() );
    }

    @Test
    public void findOnlyWarningMarkersOnJavaFile_FindsOnlyWarning() throws CoreException {
        IFile file = project.createFile( "a.java", "no content" );
        // put stuff we want to ignore
        file.createMarker( BOOKMARK );
        file.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_INFO );
        file.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_ERROR );
        // this is the guy
        file.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        workspace.buildFullyAndWait();
        assertEquals( 1, yellowCountCache().yellows() );
    }
}
