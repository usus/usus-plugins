package org.projectusus.ui.dependencygraph.action;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.projectusus.ui.dependencygraph.PackageGraphView;
import org.projectusus.ui.dependencygraph.common.DependencyGraphView;
import org.projectusus.ui.dependencygraph.filters.PackagenameNodeFilter;

public class FilterByPackageSelection implements IViewActionDelegate, ISelectionChangedListener {

    private final PackagenameNodeFilter filter = new PackagenameNodeFilter();
    private DependencyGraphView view;

    public void init( IViewPart view ) {
        this.view = (DependencyGraphView)view;
    }

    public void run( IAction action ) {
        ISelectionProvider selectionProvider = findPackageGraphSelectionProvider();
        if( action.isChecked() ) {
            selectionProvider.addSelectionChangedListener( this );
            selectionChanged( selectionProvider.getSelection() );
        } else {
            view.unsetCustomFilter();
            selectionProvider.removeSelectionChangedListener( this );
            filter.resetPackages();
        }
    }

    private ISelectionProvider findPackageGraphSelectionProvider() {
        return view.getSite().getPage().findView( PackageGraphView.VIEW_ID ).getViewSite().getSelectionProvider();
    }

    public void selectionChanged( IAction action, ISelection selection ) {
        // unused
    }

    public void selectionChanged( SelectionChangedEvent event ) {
        selectionChanged( event.getSelection() );
    }

    private void selectionChanged( ISelection selection ) {
        view.unsetCustomFilter();
        filter.setPackagesFrom( selection );
        view.setCustomFilter( filter );
    }
}
