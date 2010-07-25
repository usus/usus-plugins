// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.proportions.actions;

import static org.eclipse.ui.PlatformUI.getWorkbench;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.projectusus.ui.internal.AnalysisDisplayModel;
import org.projectusus.ui.internal.hotspots.HotSpotsView;

public class RefreshHotspots extends Action {

    private final AnalysisDisplayModel model;

    public RefreshHotspots( AnalysisDisplayModel analysisDisplayModel ) {
        super( "Refresh Hotspots" );
        model = analysisDisplayModel;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void run() {
        IViewPart viewPart = getPage().findView( HotSpotsView.class.getName() );
        if( viewPart instanceof HotSpotsView ) {
            HotSpotsView hotSpotsView = (HotSpotsView)viewPart;
            hotSpotsView.refreshActivePage( model.getEntriesOfAllCategories() );
        }

    }

    private IWorkbenchPage getPage() {
        return getWorkbench().getActiveWorkbenchWindow().getActivePage();
    }
}
