// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.project;

import static org.junit.Assert.*;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.Test;
import org.projectusus.core.internal.PDETestUsingWSProject;

public class IsUsusProjectPDETest extends PDETestUsingWSProject {

    @Test
    public void ususProject() {
        assertTrue( new IsUsusProject( project ).compute() );
    }

    @Test
    public void nonUsusProject() throws CoreException {
        makeUsusProject( false );
        assertFalse( new IsUsusProject( project ).compute() );
    }

    @Test
    public void inaccessibleProject() throws CoreException {
        project.close( new NullProgressMonitor() );
        waitForAutobuild();
        assertFalse( new IsUsusProject( project ).compute() );
        project.open( new NullProgressMonitor() ); // this fixes some following tests
    }
}
