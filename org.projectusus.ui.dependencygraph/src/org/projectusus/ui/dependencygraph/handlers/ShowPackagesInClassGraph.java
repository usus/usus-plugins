package org.projectusus.ui.dependencygraph.handlers;

import static org.eclipse.ui.handlers.HandlerUtil.getActiveSite;
import static org.eclipse.ui.handlers.HandlerUtil.getCurrentSelectionChecked;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.projectusus.ui.dependencygraph.common.DependencyGraphView;
import org.projectusus.ui.dependencygraph.filters.PackagenameNodeFilter;

public class ShowPackagesInClassGraph extends AbstractHandler {

    private static final String TARGET_VIEW_ID_PARAMETER = ShowPackagesInClassGraph.class.getName() + ".targetViewId";

    public Object execute( ExecutionEvent event ) throws ExecutionException {
        PackagenameNodeFilter filter = createFilter( event );
        DependencyGraphView view = activateTargetView( event );
        view.setCustomFilter( filter );
        return null;
    }

    public PackagenameNodeFilter createFilter( ExecutionEvent event ) throws ExecutionException {
        return PackagenameNodeFilter.from( getCurrentSelectionChecked( event ) );
    }

    public DependencyGraphView activateTargetView( ExecutionEvent event ) throws ExecutionException {
        IWorkbenchPage page = getActiveSite( event ).getPage();
        String viewId = event.getParameter( TARGET_VIEW_ID_PARAMETER );
        DependencyGraphView view = findView( viewId, page );
        if( view == null ) {
            showView( viewId, page );
            view = findView( viewId, page );
        }
        page.activate( view );
        return view;
    }

    public DependencyGraphView findView( String viewId, IWorkbenchPage page ) {
        return (DependencyGraphView)page.findView( viewId );
    }

    public void showView( String viewId, IWorkbenchPage page ) throws ExecutionException {
        try {
            page.showView( viewId );
        } catch( PartInitException exception ) {
            throw new ExecutionException( "Could not open view", exception );
        }
    }

}
