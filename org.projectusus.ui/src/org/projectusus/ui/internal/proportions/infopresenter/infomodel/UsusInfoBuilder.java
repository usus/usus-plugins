// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.infopresenter.infomodel;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.projectusus.core.internal.bugreport.BugList;
import org.projectusus.core.internal.project.IUSUSProject;

public class UsusInfoBuilder {

    private final IMethod method;

    public UsusInfoBuilder( IMethod method ) {
        this.method = method;
    }

    public IUsusInfo create() {
        return method == null ? null : buildUsusInfo();
    }

    private IUsusInfo buildUsusInfo() {
        IUsusInfo result = new UnavailableUsusInfo( method );
        try {
            result = new UsusInfo( method, findBugInfo( method ) );
        } catch( IllegalStateException isex ) {
            // something went wrong in calculation
        } catch( JavaModelException jamox ) {
            // ignore
        }
        return result;
    }

    private BugList findBugInfo( IMethod method ) throws JavaModelException {
        BugList result = new BugList();
        IProject project = method.getUnderlyingResource().getProject();
        IUSUSProject ususProject = (IUSUSProject)project.getAdapter( IUSUSProject.class );
        if( ususProject != null ) {
            result = ususProject.getBugsFor( method );
        }
        return result;
    }
}
