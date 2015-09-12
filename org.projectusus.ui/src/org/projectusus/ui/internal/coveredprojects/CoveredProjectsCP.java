// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.coveredprojects;

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.projectusus.ui.viewer.WorkspaceRootConnection;

public class CoveredProjectsCP implements IStructuredContentProvider {

    private final WorkspaceRootConnection connection = new WorkspaceRootConnection();

    public Object[] getElements( Object input ) {
        if( input instanceof IWorkspaceRoot ) {
            IWorkspaceRoot wsRoot = (IWorkspaceRoot)input;
            return wsRoot.getProjects();
        }
        return new Object[0];
    }

    public void inputChanged( Viewer viewer, Object oldInput, Object newInput ) {
        connection.update( viewer, oldInput, newInput );
    }

    public void dispose() {
        // unused
    }
}
