// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;

import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.projectusus.adapter.ICodeProportionComputationTarget;
import org.projectusus.core.internal.JavaProject;
import org.projectusus.core.internal.Workspace;
import org.projectusus.core.project2.IUSUSProject;

public class ProjectChangeNotificationsPDETest {

    @Rule
    public Workspace workspace = new Workspace();

    @Rule
    public JavaProject project = new JavaProject();

    @Rule
    public JavaProject otherProject = new JavaProject();

    private TestResourceChangeListener listener = new TestResourceChangeListener();

    @After
    public void tearDown() throws CoreException {
        getWorkspace().removeResourceChangeListener( listener );
    }

    @Test
    public void projectCreated() throws Exception {
        otherProject.delete();
        getWorkspace().addResourceChangeListener( listener );
        otherProject.create();

        assertTrue( otherProject.get().isAccessible() );
        assertTrue( otherProject.as( IUSUSProject.class ).isUsusProject() );

        listener.assertNoException();

        ICodeProportionComputationTarget target = listener.getTarget();
        assertEquals( 0, target.getRemovedProjects().size() );
        assertEquals( 1, target.getProjects().size() );
        IProject affectedProject = target.getProjects().iterator().next();
        assertEquals( otherProject.get(), affectedProject );
    }

    @Test
    public void projectClosed() throws Exception {
        getWorkspace().addResourceChangeListener( listener );
        project.close();

        listener.assertNoException();

        ICodeProportionComputationTarget target = listener.getTarget();
        assertEquals( 0, target.getProjects().size() );
        assertEquals( 1, target.getRemovedProjects().size() );
        IProject removedProject = target.getRemovedProjects().iterator().next();
        assertEquals( project.get(), removedProject );
    }

    @Test
    public void projectOpened() throws Exception {
        project.createFile( "someFile.java", "some content" );
        project.close();
        workspace.buildFullyAndWait();
        getWorkspace().addResourceChangeListener( listener );
        project.open();

        listener.assertNoException();

        ICodeProportionComputationTarget target = listener.getTarget();
        assertEquals( 1, target.getProjects().size() );
        assertEquals( 0, target.getRemovedProjects().size() );
        IProject affectedProject = target.getProjects().iterator().next();
        assertEquals( project.get(), affectedProject );
    }

    @Test
    public void projectDeleted() throws Exception {
        getWorkspace().addResourceChangeListener( listener );
        project.delete();

        listener.assertNoException();

        ICodeProportionComputationTarget target = listener.getTarget();
        assertEquals( 0, target.getProjects().size() );
        assertEquals( 1, target.getRemovedProjects().size() );
        IProject removedProject = target.getRemovedProjects().iterator().next();
        assertEquals( project.get(), removedProject );
    }
}
