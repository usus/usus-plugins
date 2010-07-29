package org.projectusus.ui.dependencygraph.filters;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.projectusus.core.basis.GraphNode;

public abstract class NodeFilter extends ViewerFilter {

    @Override
    public boolean select( Viewer viewer, Object parentElement, Object element ) {
        if( element instanceof GraphNode ) {
            return select( (GraphNode)element );
        }
        return true;
    }

    public abstract String getDescription();

    public abstract boolean select( GraphNode node );
}
