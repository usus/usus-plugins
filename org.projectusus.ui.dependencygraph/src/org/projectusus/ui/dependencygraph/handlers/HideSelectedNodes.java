package org.projectusus.ui.dependencygraph.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.projectusus.ui.dependencygraph.common.DependencyGraphView;

public class HideSelectedNodes extends AbstractHandler {

    public Object execute( ExecutionEvent event ) throws ExecutionException {
        DependencyGraphView view = (DependencyGraphView)HandlerUtil.getActivePart( event );
        view.hideSelectedNodes();
        return null;
    }
}
