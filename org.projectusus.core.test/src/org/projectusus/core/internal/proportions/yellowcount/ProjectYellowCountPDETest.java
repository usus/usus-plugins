// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.yellowcount;

import static org.eclipse.core.resources.IMarker.BOOKMARK;
import static org.eclipse.core.resources.IMarker.LINE_NUMBER;
import static org.eclipse.core.resources.IMarker.PROBLEM;
import static org.eclipse.core.resources.IMarker.SEVERITY;
import static org.eclipse.core.resources.IMarker.SEVERITY_ERROR;
import static org.eclipse.core.resources.IMarker.SEVERITY_INFO;
import static org.eclipse.core.resources.IMarker.SEVERITY_WARNING;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.junit.Test;
import org.projectusus.core.internal.PDETestUsingWSProject;
import org.projectusus.core.internal.proportions.model.IHotspot;

public class ProjectYellowCountPDETest extends PDETestUsingWSProject {

    @Test
    public void findMarkerOnProject() throws CoreException {
        project.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        ProjectYellowCount yellowCount = new ProjectYellowCount( project );

        assertEquals( 1, yellowCount.getYellowCount() );
    }

    @Test
    public void findMarkerOnFile() throws CoreException {
        createWSFile( "a", "no content" ).createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        ProjectYellowCount yellowCount = new ProjectYellowCount( project );

        assertEquals( 1, yellowCount.getYellowCount() );
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
        ProjectYellowCount yellowCount = new ProjectYellowCount( project );

        assertEquals( 2, yellowCount.getYellowCount() );
    }

    @Test
    public void findMarkersOnMultipleFiles() throws CoreException {
        createWSFile( "a", "no content" ).createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        createWSFile( "b", "no content" ).createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        ProjectYellowCount yellowCount = new ProjectYellowCount( project );

        assertEquals( 2, yellowCount.getYellowCount() );
    }
    
    @Test
    public void hotspotsOnMultipleFiles() throws CoreException {
        IFile fileA = createWSFile( "a", "no content" );
        fileA.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        IFile fileB = createWSFile( "b", "no content" );
        fileB.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        fileB.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        List<IHotspot> hotspots = new ProjectYellowCount( project ).getHotspots();

        assertEquals( 2, hotspots.size() );
        assertEquals( fileB, hotspots.get( 0 ).getFile() );
        assertEquals( 2, hotspots.get( 0 ).getHotness() );
        assertEquals( fileA, hotspots.get( 1 ).getFile() );
        assertEquals( 1, hotspots.get( 1 ).getHotness() );
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
        ProjectYellowCount yellowCount = new ProjectYellowCount( project );

        assertEquals( 1, yellowCount.getYellowCount() );
    }
}
