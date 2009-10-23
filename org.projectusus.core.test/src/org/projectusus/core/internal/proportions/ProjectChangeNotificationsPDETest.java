// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;

import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.After;
import org.junit.Test;


public class ProjectChangeNotificationsPDETest extends PDETestUsingWSProject {

    private TestResourceChangeListener listener = new TestResourceChangeListener();
    
    @After
    public void tearDown() throws CoreException {
        getWorkspace().removeResourceChangeListener( listener );
        super.tearDown();
    }

    @Test
    public void projectCreated() throws Exception {
        getWorkspace().addResourceChangeListener( listener );
        IProject otherProject = createAdditionalProjectWithFile();
        waitForAutobuild();
        
        ICodeProportionComputationTarget target = listener.getTarget();
        assertEquals( 0, target.getRemovedProjects().size() );
        assertEquals( 1, target.getProjects().size() );
        IProject affectedProject = target.getProjects().iterator().next();
        assertEquals( otherProject, affectedProject );
        
        assertNoException( listener );
    }

    
    @Test
    public void projectClosed() throws Exception {
        getWorkspace().addResourceChangeListener( listener );
        project.close( new NullProgressMonitor() );
        waitForAutobuild();
        
        ICodeProportionComputationTarget target = listener.getTarget();
        assertEquals( 0, target.getProjects().size() );
        assertEquals( 1, target.getRemovedProjects().size() );
        IProject removedProject = target.getRemovedProjects().iterator().next();
        assertEquals( project, removedProject );
        
        assertNoException( listener );
    }
    
    // TODO lf will this be enough? if the project was removed from consideration,
    // wouldn't we also need the full list of files in the project?
    
    @Test
    public void projectOpened() throws Exception {
        project.close( new NullProgressMonitor() );
        waitForAutobuild();
        getWorkspace().addResourceChangeListener( listener );
        project.open( new NullProgressMonitor() );
        waitForAutobuild();
        
        ICodeProportionComputationTarget target = listener.getTarget();
        assertEquals( 1, target.getProjects().size() );
        assertEquals( 0, target.getRemovedProjects().size() );
        IProject affectedProject = target.getProjects().iterator().next();
        assertEquals( project, affectedProject );
        
        assertNoException( listener );
    }
    
    @Test
    public void projectDeleted() throws Exception {
        waitForAutobuild();
        getWorkspace().addResourceChangeListener( listener );
        project.delete( true, new NullProgressMonitor() );
        waitForAutobuild();
        
        ICodeProportionComputationTarget target = listener.getTarget();
        assertEquals( 0, target.getProjects().size() );
        assertEquals( 1, target.getRemovedProjects().size() );
        IProject removedProject = target.getRemovedProjects().iterator().next();
        assertEquals( project, removedProject );
        
        assertNoException( listener );
    }

    private IProject createAdditionalProjectWithFile() throws CoreException {
        IProject otherProject = getWorkspace().getRoot().getProject( "anotherProject" );
        otherProject.create( new NullProgressMonitor() );
        otherProject.open( new NullProgressMonitor() );
        InputStream is = new ByteArrayInputStream( new byte[0] );
        otherProject.getFile( "blubb" ).create( is, true, new NullProgressMonitor() );
        return otherProject;
    }
}
