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
        if( action.isChecked() && selectionProvider != null ) {
            selectionProvider.addSelectionChangedListener( this );
            selectionChanged( selectionProvider.getSelection() );
        } else {
            view.unsetCustomFilter();
            if( selectionProvider != null ) {
                selectionProvider.removeSelectionChangedListener( this );
            }
            filter.resetPackages();
            action.setChecked( false );
        }
    }

    private ISelectionProvider findPackageGraphSelectionProvider() {
        IViewPart packageGraphView = view.getSite().getPage().findView( PackageGraphView.VIEW_ID );
        if( packageGraphView != null ) {
            return packageGraphView.getViewSite().getSelectionProvider();
        }
        return null;
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
