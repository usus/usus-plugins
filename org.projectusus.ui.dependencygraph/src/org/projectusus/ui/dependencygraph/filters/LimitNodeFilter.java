package org.projectusus.ui.dependencygraph.filters;

import java.util.Set;

import org.eclipse.zest.core.viewers.EntityConnectionData;
import org.projectusus.ui.dependencygraph.nodes.GraphNode;

public class LimitNodeFilter extends NodeAndEdgeFilter {

    private final IRestrictNodesFilterProvider limitProvider;

    public LimitNodeFilter( IRestrictNodesFilterProvider limitProvider ) {
        this.limitProvider = limitProvider;
    }

    @Override
    protected boolean select( GraphNode node, Set<GraphNode> others ) {
        return node.isVisibleForLimitWithOtherNodes( limit(), others );
    }

    @Override
    protected boolean select( EntityConnectionData edge, Set<GraphNode> others ) {
        if( !limit() ) {
            return true;
        }
        GraphNode source = (GraphNode)edge.source;
        GraphNode destination = (GraphNode)edge.dest;
        return select( source, others ) && select( destination, others ) && source.isInDifferentPackageThan( destination );
    }

    @Override
    public String getDescription() {
        return "Only nodes visible for limit " + limit();
    }

    private boolean limit() {
        return limitProvider.isRestricting();
    }
}
