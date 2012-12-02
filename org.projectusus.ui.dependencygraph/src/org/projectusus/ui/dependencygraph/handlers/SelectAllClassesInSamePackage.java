package org.projectusus.ui.dependencygraph.handlers;

import static org.eclipse.ui.handlers.HandlerUtil.getActivePartChecked;
import static org.eclipse.ui.handlers.HandlerUtil.getCurrentSelectionChecked;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.projectusus.jfeet.selection.ElementFrom;
import org.projectusus.ui.dependencygraph.common.DependencyGraphView;
import org.projectusus.ui.dependencygraph.nodes.GraphNode;

public class SelectAllClassesInSamePackage extends AbstractHandler {

    public Object execute( ExecutionEvent event ) throws ExecutionException {
        ISelection selection = getCurrentSelectionChecked( event );
        GraphNode node = new ElementFrom( selection ).as( GraphNode.class );
        if( node != null ) {
            DependencyGraphView view = (DependencyGraphView)getActivePartChecked( event );
            view.selectAllNodesInSamePackage( node );
        }
        return null;
    }
}
