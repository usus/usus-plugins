// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;

import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.After;
import org.junit.Test;
import org.projectusus.adapter.ICodeProportionComputationTarget;
import org.projectusus.core.internal.PDETestUsingWSProject;
import org.projectusus.core.project2.IUSUSProject;

public class ProjectChangeNotificationsPDETest extends PDETestUsingWSProject {

    private TestResourceChangeListener listener = new TestResourceChangeListener();
    private IProject otherProject = null;

    @After
    public void tearDown() throws CoreException {
        getWorkspace().removeResourceChangeListener( listener );
        if( otherProject != null ) {
            otherProject.delete( true, new NullProgressMonitor() );
            otherProject = null;
        }
        super.tearDown();
    }

    @Test
    public void projectCreated() throws Exception {
        getWorkspace().addResourceChangeListener( listener );
        otherProject = createAdditionalProjectWithFile();

        assertTrue( otherProject.isAccessible() );
        IUSUSProject ususProject = (IUSUSProject)otherProject.getAdapter( IUSUSProject.class );
        assertTrue( ususProject.isUsusProject() );

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
        project1.close( new NullProgressMonitor() );

        listener.assertNoException();

        ICodeProportionComputationTarget target = listener.getTarget();
        assertEquals( 0, target.getProjects().size() );
        assertEquals( 1, target.getRemovedProjects().size() );
        IProject removedProject = target.getRemovedProjects().iterator().next();
        assertEquals( project1, removedProject );
        project1.open( new NullProgressMonitor() ); // this fixes some following tests
    }

    @Test
    public void projectOpened() throws Exception {
        createWSFile( "someFile.java", "some content", project1 );
        project1.close( new NullProgressMonitor() );
        buildFullyAndWait();
        getWorkspace().addResourceChangeListener( listener );
        project1.open( new NullProgressMonitor() );

        listener.assertNoException();

        ICodeProportionComputationTarget target = listener.getTarget();
        assertEquals( 1, target.getProjects().size() );
        assertEquals( 0, target.getRemovedProjects().size() );
        IProject affectedProject = target.getProjects().iterator().next();
        assertEquals( project1, affectedProject );
    }

    @Test
    public void projectDeleted() throws Exception {
        getWorkspace().addResourceChangeListener( listener );
        project1.delete( true, new NullProgressMonitor() );

        listener.assertNoException();

        ICodeProportionComputationTarget target = listener.getTarget();
        assertEquals( 0, target.getProjects().size() );
        assertEquals( 1, target.getRemovedProjects().size() );
        IProject removedProject = target.getRemovedProjects().iterator().next();
        assertEquals( project1, removedProject );
    }

    private IProject createAdditionalProjectWithFile() throws CoreException {
        final IProject project = getWorkspace().getRoot().getProject( "anotherProject" );

        getWorkspace().run( new IWorkspaceRunnable() {
            public void run( IProgressMonitor monitor ) throws CoreException {
                project.create( new NullProgressMonitor() );
                project.open( new NullProgressMonitor() );
                project.getFile( "blubb.java" ).create( new ByteArrayInputStream( new byte[0] ), true, null );
                IUSUSProject ususProject = (IUSUSProject)project.getAdapter( IUSUSProject.class );
                ususProject.setUsusProject( true );
            }

        }, null );

        return project;
    }
}
