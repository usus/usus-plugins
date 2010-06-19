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
import org.junit.Test;
import org.projectusus.core.internal.proportions.rawdata.PDETestForMetricsComputation;

public class ProjectYellowCountPDETest extends PDETestForMetricsComputation {

    @Before
    public void setup() throws Exception {
        yellowCountCache().clear();
    }

    @Test
    public void findMarkerOnProject() throws CoreException {
        project.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        buildFullyAndWait();
        assertEquals( 1, yellowCountCache().yellows() );
    }

    @Test
    public void findMarkerOnJavaFile() throws CoreException {
        IFile file = createWSFile( "a.java", "no content" );
        file.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        buildFullyAndWait();
        assertEquals( 1, yellowCountCache().yellows() );
    }

    @Test
    public void findMarkerOnNonJavaFile() throws Exception {
        IFile file = createWSFile( "a", "no content" );
        file.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        buildFullyAndWait();
        assertEquals( 1, yellowCountCache().yellows() );
    }

    @Test
    public void findMultipleMarkersOnFile() throws CoreException {
        IFile file = createWSFile( "a", "no content\non two lines" );
        IMarker marker1 = file.createMarker( PROBLEM );
        marker1.setAttribute( LINE_NUMBER, 1 );
        marker1.setAttribute( SEVERITY, SEVERITY_WARNING );
        IMarker marker2 = file.createMarker( PROBLEM );
        marker2.setAttribute( LINE_NUMBER, 2 );
        marker2.setAttribute( SEVERITY, SEVERITY_WARNING );
        buildFullyAndWait();
        assertEquals( 2, yellowCountCache().yellows() );
        assertEquals( 1, yellowCountCache().yellowProjects() );
    }

    @Test
    public void findMarkersOnMultipleFiles() throws Exception {
        IFile file1 = createWSFile( "a", "no content" );
        file1.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        IFile file2 = createWSFile( "b.java", "no content" );
        file2.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        buildFullyAndWait();
        assertEquals( 2, yellowCountCache().yellows() );
    }

    @Test
    public void hotspotsOnMultipleNonJavaFiles() throws Exception {
        IFile fileA = createWSFile( "a", "no content" );
        fileA.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        IFile fileB = createWSFile( "b", "no content" );
        fileB.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        fileB.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        buildFullyAndWait();
        assertEquals( 3, yellowCountCache().yellows() );
    }

    @Test
    public void hotspotsOnMultipleJavaFiles() throws CoreException {
        IFile fileA = createWSFile( "a.java", "no content" );
        fileA.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        IFile fileB = createWSFile( "b.java", "no content" );
        fileB.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        fileB.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        buildFullyAndWait();
        assertEquals( 3, yellowCountCache().yellows() );
    }

    @Test
    public void findOnlyWarningMarkers() throws CoreException {
        IFile file = createWSFile( "a", "no content" );
        // put stuff we want to ignore
        file.createMarker( BOOKMARK );
        file.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_INFO );
        file.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_ERROR );
        // this is the guy
        file.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        buildFullyAndWait();
        assertEquals( 1, yellowCountCache().yellows() );
    }
}
