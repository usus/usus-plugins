package org.projectusus.ui.internal.proportions.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.projectusus.ui.internal.hotspots.HotSpotsView;

public class ResetSort implements IViewActionDelegate {

    private HotSpotsView view;

    public void run( IAction action ) {
        view.resetSort();
    }

    public void selectionChanged( IAction action, ISelection selection ) {
        //
    }

    public void init( IViewPart view ) {
        this.view = (HotSpotsView)view;
    }

}
