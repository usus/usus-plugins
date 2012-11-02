// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.project;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.projectusus.core.project2.UsusProjectSupport.asUsusProject;

import org.eclipse.core.runtime.CoreException;
import org.junit.Rule;
import org.junit.Test;
import org.projectusus.core.internal.JavaProject;
import org.projectusus.core.internal.Workspace;

public class IsUsusProjectPDETest {

    @Rule
    public Workspace workspace = new Workspace();

    @Rule
    public JavaProject project = new JavaProject();

    @Test
    public void ususProject() {
        assertTrue( asUsusProject( project.get() ).isUsusProject() );
    }

    @Test
    public void nonUsusProject() throws CoreException {
        project.disableUsus();
        assertFalse( asUsusProject( project.get() ).isUsusProject() );
    }

    @Test
    public void inaccessibleProject() throws CoreException {
        project.close();
        workspace.buildFullyAndWait();
        assertFalse( asUsusProject( project.get() ).isUsusProject() );
    }
}
