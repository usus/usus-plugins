package org.projectusus.ui.dependencygraph.filters;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.projectusus.ui.dependencygraph.nodes.GraphNode;

import com.google.common.base.Joiner;

public class DirectNeighboursFilter extends NodeAndEdgeFilter {

    private Set<GraphNode> directNeighbours = new HashSet<GraphNode>();
    private List<String> selectedNodeNames = new ArrayList<String>();

    public DirectNeighboursFilter( Set<GraphNode> selectedNodes ) {
        selectedNodeNames = new ArrayList<String>();
        directNeighbours = new HashSet<GraphNode>();
        for( GraphNode selectedNode : selectedNodes ) {
            directNeighbours.add( selectedNode );
            directNeighbours.addAll( selectedNode.getChildren() );
            directNeighbours.addAll( selectedNode.getParents() );
            selectedNodeNames.add( selectedNode.getDisplayText() );
        }
    }

    @Override
    public String getDescription() {
        return "Direct neighbours of " + Joiner.on( ", " ).join( selectedNodeNames );
    }

    @Override
    protected boolean select( GraphNode node, Set<GraphNode> others ) {
        return directNeighbours.contains( node );
    }

}
