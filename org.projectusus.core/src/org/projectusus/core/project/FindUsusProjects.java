// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.project;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static org.projectusus.core.project2.UsusProjectSupport.isUsusProject;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;

public class FindUsusProjects {

    private final List<IProject> candidates;

    private static List<IProject> asProjectList( Object[] elements ) {
        List<IProject> projects = new ArrayList<IProject>();
        for( Object element : elements ) {
            if( element instanceof IProject ) {
                projects.add( (IProject)element );
            }
        }
        return projects;
    }

    public FindUsusProjects( List<IProject> candidates ) {
        this.candidates = candidates;
    }

    public FindUsusProjects( IProject[] candidates ) {
        this( asList( candidates ) );
    }

    public FindUsusProjects( Object[] candidates ) {
        this( asProjectList( candidates ) );
    }

    public List<IProject> compute() {
        return compute( true );
    }

    public List<IProject> computeOpposite() {
        return compute( false );
    }

    private List<IProject> compute( boolean findUsusProjects ) {
        List<IProject> result = new ArrayList<IProject>();
        for( IProject project : candidates ) {
            if( findUsusProjects == isUsusProject( project ) ) {
                result.add( project );
            }
        }
        return unmodifiableList( result );
    }

}
