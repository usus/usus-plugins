package org.projectusus.ui.dependencygraph.handlers;

import static org.eclipse.ui.handlers.HandlerUtil.getActiveSite;
import static org.eclipse.ui.handlers.HandlerUtil.getCurrentSelectionChecked;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.projectusus.ui.dependencygraph.filters.PackagenameNodeFilter;

public class ShowInClassGraph extends AbstractHandler {

    private static final String TARGET_VIEW_ID_PARAMETER = ShowInClassGraph.class.getName() + ".targetViewId";

    public Object execute( ExecutionEvent event ) throws ExecutionException {
        PackagenameNodeFilter filter = createFilter( event );
        String viewId = event.getParameter( TARGET_VIEW_ID_PARAMETER );
        new DependencyGraphViewFilterer( getActiveSite( event ).getPage() ).applyFilterToView( viewId, filter );
        return null;
    }

    private PackagenameNodeFilter createFilter( ExecutionEvent event ) throws ExecutionException {
        return PackagenameNodeFilter.from( getCurrentSelectionChecked( event ) );
    }

}
