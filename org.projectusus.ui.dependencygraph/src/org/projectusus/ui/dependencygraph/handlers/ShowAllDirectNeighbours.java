package org.projectusus.ui.dependencygraph.handlers;

import static org.eclipse.ui.handlers.HandlerUtil.getActivePartChecked;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.projectusus.ui.dependencygraph.common.DependencyGraphView;

public class ShowAllDirectNeighbours extends AbstractHandler {

    public Object execute( ExecutionEvent event ) throws ExecutionException {
        DependencyGraphView view = (DependencyGraphView)getActivePartChecked( event );
        view.showAllDirectNeighbours();
        view.applyLayout();
        return null;
    }
}
