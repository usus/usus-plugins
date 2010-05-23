package org.projectusus.ui.dependencygraph.common;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.projectusus.core.basis.GraphNode;

public class NodeFilter extends ViewerFilter {

    private final FilterLimitProvider limitProvider;

    public NodeFilter( FilterLimitProvider limitProvider ) {
        this.limitProvider = limitProvider;
    }

    @Override
    public boolean select( Viewer viewer, Object parentElement, Object element ) {
        if( element instanceof GraphNode ) {
            GraphNode classRepresenter = (GraphNode)element;
            return classRepresenter.isVisibleFor( limitProvider.getFilterLimit() );
        }
        return true;
    }
}
