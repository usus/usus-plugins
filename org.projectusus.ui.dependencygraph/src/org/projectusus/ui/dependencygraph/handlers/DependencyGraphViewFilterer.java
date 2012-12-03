package org.projectusus.ui.dependencygraph.handlers;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.projectusus.ui.dependencygraph.common.DependencyGraphView;
import org.projectusus.ui.dependencygraph.filters.NodeAndEdgeFilter;

public class DependencyGraphViewFilterer {

    private final IWorkbenchPage page;

    public DependencyGraphViewFilterer( IWorkbenchPage page ) {
        this.page = page;
    }

    public void applyFilterToView( String viewId, NodeAndEdgeFilter filter ) throws ExecutionException {
        activateView( viewId ).setCustomFilter( filter );
    }

    private DependencyGraphView activateView( String viewId ) throws ExecutionException {
        DependencyGraphView view = findView( viewId, page );
        if( view == null ) {
            showView( viewId, page );
            view = findView( viewId, page );
        }
        page.activate( view );
        return view;
    }

    private DependencyGraphView findView( String viewId, IWorkbenchPage page ) {
        return (DependencyGraphView)page.findView( viewId );
    }

    private void showView( String viewId, IWorkbenchPage page ) throws ExecutionException {
        try {
            page.showView( viewId );
        } catch( PartInitException exception ) {
            throw new ExecutionException( "Could not open view", exception );
        }
    }

}
