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

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.internal.UsusCorePlugin;
import org.projectusus.core.internal.proportions.IUsusModel;
import org.projectusus.core.internal.proportions.IUsusModelWriteAccess;
import org.projectusus.core.internal.proportions.model.CodeProportion;
import org.projectusus.core.internal.proportions.model.IHotspot;
import org.projectusus.core.internal.proportions.rawdata.CodeProportionKind;
import org.projectusus.core.internal.proportions.rawdata.PDETestForMetricsComputation;

public class ProjectYellowCountPDETest extends PDETestForMetricsComputation {

    private IUsusModel model;
    private IUsusModelWriteAccess writeModel;

    @Before
    public void setup() throws CoreException{
        writeModel = UsusCorePlugin.getUsusModelWriteAccess();
        writeModel.dropRawData( project );
        makeUsusProject( true );
        addJavaNature();
        model = UsusCorePlugin.getUsusModel();
   }

    @Test
    public void findMarkerOnProject() throws CoreException {
        project.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        waitForAutobuild();
        int yellowCount = model.getOverallMetric( project, CodeProportionKind.CW );
        assertEquals( 1, yellowCount );
    }

    @Test
    public void findMarkerOnJavaFile() throws CoreException {
        IFile file = createWSFile( "a.java", "no content" );
        file.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        waitForAutobuild();
        int yellowCount = model.getOverallMetric( project, CodeProportionKind.CW );

        assertEquals( 1, yellowCount );
    }

    @Test
    public void findMarkerOnNonJavaFile() throws Exception {
        IFile file = createWSFile( "a", "no content" );
        file.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        waitForAutobuild();
        int yellowCount = model.getOverallMetric( project, CodeProportionKind.CW );
        
        assertEquals( 1, yellowCount );
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
        waitForAutobuild();
        int yellowCount = model.getOverallMetric( project, CodeProportionKind.CW );
        int violations = model.getViolationCount( project, CodeProportionKind.CW );

        assertEquals( 2, yellowCount );
        assertEquals(1, violations);
    }

    @Test
    public void findMarkersOnMultipleFiles() throws Exception {
        IFile file1 = createWSFile( "a", "no content" );
        file1.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        IFile file2 = createWSFile( "b.java", "no content" );
        file2.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        waitForAutobuild();
        int yellowCount = model.getOverallMetric( project, CodeProportionKind.CW );

        assertEquals( 2, yellowCount );
    }
    
    @Test
    public void hotspotsOnMultipleNonJavaFiles() throws Exception {
        IFile fileA = createWSFile( "a", "no content" );
        fileA.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        IFile fileB = createWSFile( "b", "no content" );
        fileB.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        fileB.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        waitForAutobuild();
       CodeProportion yellowCount = model.getCodeProportion( CodeProportionKind.CW );

        List<IHotspot> hotspots = yellowCount.getHotspots();
        assertEquals( 2, hotspots.size() );
        assertEquals( fileB, hotspots.get( 0 ).getFile() );
        assertEquals( 2, hotspots.get( 0 ).getHotness() );
        assertEquals( fileA, hotspots.get( 1 ).getFile() );
        assertEquals( 1, hotspots.get( 1 ).getHotness() );
    }

    @Test
    public void hotspotsOnMultipleJavaFiles() throws CoreException {
        IFile fileA = createWSFile( "a.java", "no content" );
        fileA.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        IFile fileB = createWSFile( "b.java", "no content" );
        fileB.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        fileB.createMarker( PROBLEM ).setAttribute( SEVERITY, SEVERITY_WARNING );
        waitForAutobuild();
        CodeProportion yellowCount = model.getCodeProportion( CodeProportionKind.CW );
        
        List<IHotspot> hotspots = yellowCount.getHotspots();
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
        waitForAutobuild();
        int yellowCount = model.getOverallMetric( project, CodeProportionKind.CW );

        assertEquals( 1, yellowCount );
    }
}
