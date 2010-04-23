package org.projectusus.ui.dependencygraph.action;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.projectusus.ui.dependencygraph.common.DependencyGraphView;

public class RefreshGraphView implements IViewActionDelegate {

    private DependencyGraphView graphView;

    public void run( IAction arg0 ) {
        graphView.refresh();
    }

    public void init( IViewPart viewPart ) {
        this.graphView = (DependencyGraphView)viewPart;
    }

    public void selectionChanged( IAction arg0, ISelection arg1 ) {
        // unused
    }

}
