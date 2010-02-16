// Copyright (c) 2009-2010 by the projectusus.org contributors
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
import org.projectusus.core.internal.PDETestUsingWSProject;
import org.projectusus.core.internal.project.IUSUSProject;
import org.projectusus.core.internal.proportions.modelcomputation.ICodeProportionComputationTarget;


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
        
        listener.assertNoException();
        
        ICodeProportionComputationTarget target = listener.getTarget();
        assertEquals( 0, target.getRemovedProjects().size() );
        assertEquals( 1, target.getProjects().size() );
        IProject affectedProject = target.getProjects().iterator().next();
        assertEquals( otherProject, affectedProject );
    }

    
    @Test
    public void projectClosed() throws Exception {
        getWorkspace().addResourceChangeListener( listener );
        project.close( new NullProgressMonitor() );
        waitForAutobuild();
        
        listener.assertNoException();
        
        ICodeProportionComputationTarget target = listener.getTarget();
        assertEquals( 0, target.getProjects().size() );
        assertEquals( 1, target.getRemovedProjects().size() );
        IProject removedProject = target.getRemovedProjects().iterator().next();
        assertEquals( project, removedProject );
    }
    
    // TODO lf will this be enough? if the project was removed from consideration,
    // wouldn't we also need the full list of files in the project?
    
    @Test
    public void projectOpened() throws Exception {
        createWSFile( "someFile.java", "some content" );
        project.close( new NullProgressMonitor() );
        waitForAutobuild();
        getWorkspace().addResourceChangeListener( listener );
        project.open( new NullProgressMonitor() );
        waitForAutobuild();
        
        listener.assertNoException();
        
        ICodeProportionComputationTarget target = listener.getTarget();
        assertEquals( 1, target.getProjects().size() );
        assertEquals( 0, target.getRemovedProjects().size() );
        IProject affectedProject = target.getProjects().iterator().next();
        assertEquals( project, affectedProject );
    }
    
    @Test
    public void projectDeleted() throws Exception {
        waitForAutobuild();
        getWorkspace().addResourceChangeListener( listener );
        project.delete( true, new NullProgressMonitor() );
        waitForAutobuild();
        
        listener.assertNoException();
        
        ICodeProportionComputationTarget target = listener.getTarget();
        assertEquals( 0, target.getProjects().size() );
        assertEquals( 1, target.getRemovedProjects().size() );
        IProject removedProject = target.getRemovedProjects().iterator().next();
        assertEquals( project, removedProject );
    }

    private IProject createAdditionalProjectWithFile() throws CoreException {
        IProject result = getWorkspace().getRoot().getProject( "anotherProject" );
        result.create( new NullProgressMonitor() );
        result.open( new NullProgressMonitor() );
        InputStream is = new ByteArrayInputStream( new byte[0] );
        result.getFile( "blubb.java" ).create( is, true, new NullProgressMonitor() );
        IUSUSProject ususProject = (IUSUSProject)result.getAdapter( IUSUSProject.class );
        ususProject.setUsusProject( true );
        return result;
    }
}
