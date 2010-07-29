package org.projectusus.ui.dependencygraph.filters;

import org.projectusus.core.basis.GraphNode;

public class LimitNodeFilter extends NodeFilter {

    private final IFilterLimitProvider limitProvider;

    public LimitNodeFilter( IFilterLimitProvider limitProvider ) {
        this.limitProvider = limitProvider;
    }

    @Override
    public boolean select( GraphNode node ) {
        return node.isVisibleFor( limit() );
    }

    @Override
    public String getDescription() {
        return "Only nodes visible for limit " + limit();
    }

    private int limit() {
        return limitProvider.getFilterLimit();
    }
}
