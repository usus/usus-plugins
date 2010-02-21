// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.autotestsuite.core.internal;

import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;

/**
 * test container graph over all projects in the workspace.
 * 
 * @author leif
 */
public class WorkspaceTestContainerGraph extends TestContainerGraph {

    public WorkspaceTestContainerGraph() {
        super( getWorkspace().getRoot().getProjects() );
    }
}
