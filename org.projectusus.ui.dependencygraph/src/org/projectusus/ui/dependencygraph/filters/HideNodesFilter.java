package org.projectusus.ui.dependencygraph.filters;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.projectusus.core.basis.GraphNode;

public class HideNodesFilter extends NodeFilter {

    Set<GraphNode> nodesToHide = new HashSet<GraphNode>();

    @Override
    public String getDescription() {
        return "Hides the following nodes: ";
    }

    @Override
    public boolean select( GraphNode node ) {
        return !nodesToHide.contains( node );
    }

    public void addNodesToHide( Collection<GraphNode> selectedNodes ) {
        nodesToHide.addAll( selectedNodes );
    }

    public void reset() {
        nodesToHide.clear();
    }
}
