// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;

import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;
import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.junit.After;
import org.junit.Test;
import org.projectusus.adapter.ICodeProportionComputationTarget;
import org.projectusus.core.internal.PDETestUsingWSProject;

public class UsusProjectNotificationsPDETest extends PDETestUsingWSProject {

    private TestResourceChangeListener listener = new TestResourceChangeListener();

    @After
    public void tearDown() throws CoreException {
        getWorkspace().removeResourceChangeListener( listener );
        super.tearDown();
    }

    @Test
    public void projectAddedToUsus() throws Exception {
        makeUsusProject( false, project1 );
        makeUsusProject( false, project2 );
        buildFullyAndWait();
        getWorkspace().addResourceChangeListener( listener );
        makeUsusProject( true, project1 );
        buildFullyAndWait();

        listener.assertNoException();

        ICodeProportionComputationTarget target = listener.getTarget();
        assertEquals( 0, target.getRemovedProjects().size() );
        assertEquals( 1, target.getProjects().size() );
        IProject affectedProject = target.getProjects().iterator().next();
        assertEquals( project1, affectedProject );
        Collection<IFile> files = target.getFiles( project1 );
        assertEquals( 1, files.size() );
        assertEquals( "org.projectusus.core.prefs", files.iterator().next().getName() );
    }

    @Test
    public void projectRemovedFromUsus() throws Exception {
        getWorkspace().addResourceChangeListener( listener );
        createWSFile( "a.java", "created before removing project from usus", project1 );

        listener.assertNoException();
        assertEquals( 1, listener.getTarget().getProjects().size() );

        makeUsusProject( false, project1 );
        buildFullyAndWait();

        listener.assertNoException();

        ICodeProportionComputationTarget target = listener.getTarget();
        assertEquals( 0, target.getProjects().size() );
        assertRemovedProject( target );
    }

    @Test
    public void excludeNonUsusProjects() throws Exception {
        makeUsusProject( false, project1 );
        getWorkspace().addResourceChangeListener( listener );
        createWSFile( "a.java", "file that is ignored because in non-usus project", project1 );

        listener.assertNoException();

        ICodeProportionComputationTarget target = listener.getTarget();
        // non-Usus projects always show up in the list of removed projects
        // otherwise we would have a gap when switching a project from
        // added-to-Usus to not-added-to-Usus

        assertRemovedProject( target );
        assertEquals( 0, target.getProjects().size() );
    }

    private void assertRemovedProject( ICodeProportionComputationTarget target ) {
        assertEquals( 1, target.getRemovedProjects().size() );
        IProject removedProject = target.getRemovedProjects().iterator().next();
        assertEquals( project1, removedProject );
    }
}
