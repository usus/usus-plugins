// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.coveredprojects;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Display;

class RefreshViewerOnResourceChange implements IResourceChangeListener {

    private final Viewer viewer;

    RefreshViewerOnResourceChange( Viewer viewer ) {
        this.viewer = viewer;
    }

    public void resourceChanged( IResourceChangeEvent event ) {
        if( viewer != null && !viewer.getControl().isDisposed() ) {
            Display.getDefault().asyncExec( new Runnable() {
                public void run() {
                    viewer.refresh();
                }
            } );
        }
    }
}
