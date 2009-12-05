// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.actions;

import static org.eclipse.ui.PlatformUI.getWorkbench;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.projectusus.ui.internal.UsusUIPlugin;

class ShowViewById extends Action {

    private final String viewId;

    ShowViewById( String label, String viewId ) {
        super( label );
        this.viewId = viewId;
    }

    @Override
    public void run() {
        try {
            getPage().showView( viewId );
        } catch( PartInitException paix ) {
            UsusUIPlugin.getDefault().getLog().log( paix.getStatus() );
        }
    }

    private IWorkbenchPage getPage() {
        return getWorkbench().getActiveWorkbenchWindow().getActivePage();
    }
}
