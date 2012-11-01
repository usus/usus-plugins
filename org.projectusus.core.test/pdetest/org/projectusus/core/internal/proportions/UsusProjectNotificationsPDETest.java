// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;

import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;
import static org.junit.Assert.assertEquals;

import org.eclipse.core.resources.IProject;
import org.junit.After;
import org.junit.Test;
import org.projectusus.adapter.ICodeProportionComputationTarget;
import org.projectusus.core.basis.MetricsResults;
import org.projectusus.core.internal.PDETestUsingWSProject;
import org.projectusus.core.statistics.DefaultMetricsResultVisitor;

public class UsusProjectNotificationsPDETest extends PDETestUsingWSProject {

    private final class ProjectCountingVisitor extends DefaultMetricsResultVisitor {
        int projects = 0;

        public void inspectProject( MetricsResults results ) {
            projects++;
        }
    }

    private TestResourceChangeListener listener = new TestResourceChangeListener();

    @After
    public void removeResourceChangeListener() {
        getWorkspace().removeResourceChangeListener( listener );
    }

    @Test
    public void projectAddedToUsus() throws Exception {
        createWSFile( "A.java", "class A {}", project1 );
        makeUsusProject( false, project1 );
        buildFullyAndWait();

        assertEquals( 0, countProjects() );

        makeUsusProject( true, project1 );
        buildFullyAndWait();

        assertEquals( 1, countProjects() );
    }

    @Test
    public void projectRemovedFromUsus() throws Exception {
        createWSFile( "A.java", "class A {}", project1 );
        createWSFile( "B.java", "class B {}", project2 );
        buildFullyAndWait();
        assertEquals( 2, countProjects() );

        makeUsusProject( false, project2 );
        buildFullyAndWait();
        assertEquals( 1, countProjects() );

        makeUsusProject( false, project1 );
        buildFullyAndWait();
        assertEquals( 0, countProjects() );
    }

    private int countProjects() {
        ProjectCountingVisitor visitor = new ProjectCountingVisitor();
        visitor.visit();
        return visitor.projects;
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
