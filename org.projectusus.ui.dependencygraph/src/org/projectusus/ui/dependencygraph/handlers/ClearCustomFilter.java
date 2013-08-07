package org.projectusus.ui.dependencygraph.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.projectusus.ui.dependencygraph.common.DependencyGraphView;

public class ClearCustomFilter extends AbstractHandler {

    public Object execute( ExecutionEvent event ) throws ExecutionException {
        // we get here when the user presses the eraser icon
        DependencyGraphView view = (DependencyGraphView)HandlerUtil.getActivePart( event );
        view.clearCustomFilter();
        view.clearNeighboursFilter();
        view.resetHiddenNodes();
        view.drawGraphConditionally();
        view.applyLayout();
        return null;
    }
}
