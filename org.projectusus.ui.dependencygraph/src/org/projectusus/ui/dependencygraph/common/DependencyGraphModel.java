package org.projectusus.ui.dependencygraph.common;

import java.util.Set;

import org.projectusus.core.basis.GraphNode;

public abstract class DependencyGraphModel {

    private Set<? extends GraphNode> nodes;
    private boolean changed = false;

    public boolean isChanged() {
        return nodes == null || changed;
    }

    public void resetChanged() {
        this.changed = false;
    }

    public Set<? extends GraphNode> getGraphNodes() {
        if( nodes == null ) {
            nodes = getRefreshedNodes();
        }
        return nodes;
    }

    protected abstract Set<? extends GraphNode> getRefreshedNodes();

    public void invalidate() {
        Set<? extends GraphNode> freshNodes = getRefreshedNodes();
        if( nodes != null ) {
            if( !freshNodes.equals( nodes ) ) {
                nodes = freshNodes;
                changed = true;
            }
        }
    }

    public abstract int getMaxFilterValue();
}
