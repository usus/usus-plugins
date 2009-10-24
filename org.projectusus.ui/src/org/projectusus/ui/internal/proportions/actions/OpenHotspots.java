// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.actions;

import static org.eclipse.ui.PlatformUI.getWorkbench;
import static org.projectusus.ui.internal.util.UITexts.openHotspots_label;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.projectusus.core.internal.proportions.CodeProportion;
import org.projectusus.ui.internal.UsusUIPlugin;
import org.projectusus.ui.internal.hotspots.HotSpotsView;

public class OpenHotspots extends Action {

    private final ISelection selection;

    public OpenHotspots( ISelection selection ) {
        super( openHotspots_label ); // TODO lf img desc
        this.selection = selection;
    }

    @Override
    public boolean isEnabled() {
        boolean result = false;
        if( !selection.isEmpty() && selection instanceof IStructuredSelection ) {
            IStructuredSelection ssel = (IStructuredSelection)selection;
            result = ssel.getFirstElement() instanceof CodeProportion;
        }
        return result;
    }

    @Override
    public void run() {
        if( !selection.isEmpty() && selection instanceof IStructuredSelection ) {
            IStructuredSelection ssel = (IStructuredSelection)selection;
            Object element = ssel.getFirstElement();
            if( element instanceof CodeProportion ) {
                showHotspotsView();
            }
        }
    }

    private void showHotspotsView() {
        try {
            IWorkbenchWindow window = getWorkbench().getActiveWorkbenchWindow();
            window.getActivePage().showView( HotSpotsView.class.getName() );
        } catch( PartInitException paix ) {
            UsusUIPlugin.getDefault().getLog().log( paix.getStatus() );
        }
    }

}
