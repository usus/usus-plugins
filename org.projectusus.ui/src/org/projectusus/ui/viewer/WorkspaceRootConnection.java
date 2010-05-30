// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.viewer;

import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.jface.viewers.Viewer;

public class WorkspaceRootConnection {

    private IResourceChangeListener listener;

    public void update( Viewer viewer, Object oldInput, Object newInput ) {
        disconnectFrom( oldInput );
        connectTo( newInput, viewer );
    }

    private void connectTo( Object input, Viewer viewer ) {
        if( input instanceof IWorkspaceRoot ) {
            this.listener = createListener( viewer );
            toWorkspace( input ).addResourceChangeListener( listener );
        }
    }

    private IWorkspace toWorkspace( Object input ) {
        return ((IWorkspaceRoot)input).getWorkspace();
    }

    private void disconnectFrom( Object input ) {
        if( input instanceof IWorkspaceRoot && listener != null ) {
            toWorkspace( input ).removeResourceChangeListener( listener );
            listener = null;
        }
    }

    protected IResourceChangeListener createListener( Viewer viewer ) {
        return new RefreshViewerOnResourceChange( viewer );
    }
}
