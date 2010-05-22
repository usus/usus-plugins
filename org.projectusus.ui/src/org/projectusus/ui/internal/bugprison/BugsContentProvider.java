// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.bugprison;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.projectusus.bugprison.core.BugList;
import org.projectusus.bugprison.core.IBuggyProject;
import org.projectusus.ui.internal.coveredprojects.CoveredProjectsCP;

public class BugsContentProvider extends CoveredProjectsCP {

    @Override
    public Object[] getElements( Object input ) {
        BugList bugList = new BugList();
        if( input instanceof IWorkspaceRoot ) {
            IWorkspaceRoot wsRoot = (IWorkspaceRoot)input;
            for( IProject project : wsRoot.getProjects() ) {
                IBuggyProject ususProject = (IBuggyProject)project.getAdapter( IBuggyProject.class );
                bugList.addBugs( ususProject.getBugs() );
            }
        }
        return bugList.getBugs();
    }

}
