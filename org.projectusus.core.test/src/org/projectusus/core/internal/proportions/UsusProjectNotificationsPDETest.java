// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions;



import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;
import static org.junit.Assert.assertEquals;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.junit.After;
import org.junit.Test;
import org.projectusus.core.internal.project.IUSUSProject;


public class UsusProjectNotificationsPDETest extends PDETestUsingWSProject {

    // TODO lf test cases:
    //
    // project added and removed from usus projects

    private TestResourceChangeListener listener = new TestResourceChangeListener();
    
    @After
    public void tearDown() throws CoreException {
        getWorkspace().removeResourceChangeListener( listener );
        super.tearDown();
    }

    // TODO lf same game as with project creation, is this enough?
    
    // TODO lf also: need we a usus project listener and send a special delta?
    
    @Test
    public void projectAddedToUsus() throws Exception {
        getWorkspace().addResourceChangeListener( listener );
        makeUsusProject();
        waitForAutobuild();
        
        ICodeProportionComputationTarget target = listener.getTarget();
        assertEquals( 0, target.getRemovedProjects().size() );
        assertEquals( 1, target.getProjects().size() );
        IProject affectedProject = target.getProjects().iterator().next();
        assertEquals( project, affectedProject );
        
        assertNoException( listener );
    }

    private void makeUsusProject() {
        Object adapter = project.getAdapter( IUSUSProject.class );
        ((IUSUSProject)adapter).setUsusProject( true );
    }
}
