// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.actions;

import static org.eclipse.ui.PlatformUI.getWorkbench;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.projectusus.ui.internal.AnalysisDisplayEntry;
import org.projectusus.ui.internal.UsusUIPlugin;
import org.projectusus.ui.internal.hotspots.HotSpotsView;

public class OpenHotspots extends Action {

    private final ISelection selection;

    public OpenHotspots( ISelection selection ) {
        super( "Open Hotspots" ); //$NON-NLS-1$
        this.selection = selection;
    }

    @Override
    public boolean isEnabled() {
        boolean result = false;
        if( !selection.isEmpty() && selection instanceof IStructuredSelection ) {
            IStructuredSelection ssel = (IStructuredSelection)selection;
            result = ssel.getFirstElement() instanceof AnalysisDisplayEntry;
        }
        return result;
    }

    @Override
    public void run() {
        if( !selection.isEmpty() && selection instanceof IStructuredSelection ) {
            Object element = ((IStructuredSelection)selection).getFirstElement();
            if( element instanceof AnalysisDisplayEntry ) {
                showHotspotsView( ((AnalysisDisplayEntry)element) );
            }
        }
    }

    private void showHotspotsView( AnalysisDisplayEntry entry ) {
        try {
            IViewPart viewPart = getPage().showView( HotSpotsView.class.getName() );
            if( viewPart instanceof HotSpotsView ) {
                ((HotSpotsView)viewPart).update( entry );
            }
        } catch( PartInitException paix ) {
            UsusUIPlugin.getDefault().getLog().log( paix.getStatus() );
        }
    }

    private IWorkbenchPage getPage() {
        return getWorkbench().getActiveWorkbenchWindow().getActivePage();
    }
}
