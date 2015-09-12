package org.projectusus.ui.dependencygraph.filters;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.projectusus.ui.dependencygraph.nodes.GraphNode;

public class HideNodesFilter extends NodeAndEdgeFilter {

    Set<GraphNode> nodesToHide = new HashSet<GraphNode>();

    @Override
    public String getDescription() {
        return "Hides the following nodes: ";
    }

    @Override
    protected boolean select( GraphNode node, Set<GraphNode> others ) {
        return !nodesToHide.contains( node );
    }

    public void addNodesToHide( Collection<GraphNode> selectedNodes ) {
        nodesToHide.addAll( selectedNodes );
    }

    public void reset() {
        nodesToHide.clear();
    }
}
