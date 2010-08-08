// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.project;

import static org.junit.Assert.*;
import static org.projectusus.core.project.UsusProjectSupport.isUsusProject;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.Test;
import org.projectusus.core.internal.PDETestUsingWSProject;

public class IsUsusProjectPDETest extends PDETestUsingWSProject {

    @Test
    public void ususProject() {
        assertTrue(  isUsusProject( project1 ));
    }

    @Test
    public void nonUsusProject() throws CoreException {
        makeUsusProject( false, project1 );
        assertFalse( isUsusProject( project1 ));
    }

    @Test
    public void inaccessibleProject() throws CoreException {
        project1.close( new NullProgressMonitor() );
        buildFullyAndWait();
        assertFalse( isUsusProject( project1 ));
        project1.open( new NullProgressMonitor() ); // this fixes some following tests
    }
}
