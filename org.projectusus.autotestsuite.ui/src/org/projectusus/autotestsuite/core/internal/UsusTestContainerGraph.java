// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.autotestsuite.core.internal;

import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.projectusus.core.project.FindUsusProjects;

/**
 * test container graph over all projects selected for use with Usus.
 * 
 * @author leif
 */
public class UsusTestContainerGraph extends TestContainerGraph {

    public UsusTestContainerGraph() {
        super( findUsusProjects() );
    }

    private static IProject[] findUsusProjects() {
        List<IProject> result = new FindUsusProjects( getWSProjects() ).compute();
        return result.toArray( new IProject[result.size()] );
    }

    private static IProject[] getWSProjects() {
        return getWorkspace().getRoot().getProjects();
    }
}
