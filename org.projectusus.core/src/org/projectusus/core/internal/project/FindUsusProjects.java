// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.project;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;

public class FindUsusProjects {

    private final List<IProject> candidates;

    public FindUsusProjects( List<IProject> candidates ) {
        this.candidates = candidates;
    }

    public FindUsusProjects( IProject[] candidates ) {
        this.candidates = asList( candidates );
    }

    public FindUsusProjects( Object[] candidates ) {
        this.candidates = asProjectList( candidates );
    }

    public List<IProject> compute() {
        List<IProject> result = new ArrayList<IProject>();
        for( IProject project : candidates ) {
            if( isUsusProject( project ) ) {
                result.add( project );
            }
        }
        return unmodifiableList( result );
    }

    private boolean isUsusProject( IProject project ) {
        boolean result = false;
        if( project.isAccessible() ) {
            Object adapter = project.getAdapter( IUSUSProject.class );
            if( adapter instanceof IUSUSProject ) {
                result = ((IUSUSProject)adapter).isUsusProject();
            }
        }
        return result;
    }

    private List<IProject> asProjectList( Object[] elements ) {
        List<IProject> projects = new ArrayList<IProject>();
        for( Object element : elements ) {
            if( element instanceof IProject ) {
                projects.add( (IProject)element );
            }
        }
        return projects;
    }
}
