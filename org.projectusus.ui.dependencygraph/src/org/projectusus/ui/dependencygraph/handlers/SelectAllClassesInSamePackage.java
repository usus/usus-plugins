package org.projectusus.ui.dependencygraph.handlers;

import static org.eclipse.ui.handlers.HandlerUtil.getActivePartChecked;
import static org.eclipse.ui.handlers.HandlerUtil.getCurrentSelectionChecked;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.projectusus.core.basis.GraphNode;
import org.projectusus.jfeet.selection.ElementFrom;
import org.projectusus.ui.dependencygraph.common.DependencyGraphView;

public class SelectAllClassesInSamePackage extends AbstractHandler {

    public Object execute( ExecutionEvent event ) throws ExecutionException {
        ISelection selection = getCurrentSelectionChecked( event );
        GraphNode node = new ElementFrom( selection ).as( GraphNode.class );
        DependencyGraphView view = (DependencyGraphView)getActivePartChecked( event );
        view.selectAllNodesInSamePackage( node );
        return null;
    }
}
