package org.projectusus.ui.dependencygraph.filters;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.zest.core.viewers.EntityConnectionData;
import org.projectusus.ui.dependencygraph.nodes.GraphNode;

public abstract class NodeAndEdgeFilter extends ViewerFilter {

    @Override
    public boolean select( Viewer viewer, Object parentElement, Object element ) {
        Set<GraphNode> others = convertToSetOfGraphNodes( parentElement );
        if( element instanceof GraphNode ) {
            return select( (GraphNode)element, others );
        }
        if( element instanceof EntityConnectionData ) {
            return select( (EntityConnectionData)element, others );
        }
        return true;
    }

    public abstract String getDescription();

    protected abstract boolean select( GraphNode node, Set<GraphNode> others );

    protected boolean select( EntityConnectionData edge, Set<GraphNode> others ) {
        return true;
    }

    private Set<GraphNode> convertToSetOfGraphNodes( Object parentElement ) {
        Set<GraphNode> others = new HashSet<GraphNode>();
        if( parentElement instanceof Set ) {
            Set<?> parentSet = (Set<?>)parentElement;
            for( Object object : parentSet ) {
                if( object instanceof GraphNode ) {
                    others.add( (GraphNode)object );
                }
            }
        }
        return others;
    }

    public void setFilterLimitProvider( IRestrictNodesFilterProvider filterLimitProvider ) {
        // no limit
    }

}
