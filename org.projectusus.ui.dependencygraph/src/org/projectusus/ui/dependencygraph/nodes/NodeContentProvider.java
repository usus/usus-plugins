package org.projectusus.ui.dependencygraph.nodes;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.zest.core.viewers.IGraphEntityContentProvider;

public class NodeContentProvider extends ArrayContentProvider implements IGraphEntityContentProvider {

    public Object[] getConnectedTo( Object entity ) {
        if( entity instanceof GraphNode ) {
            GraphNode node = (GraphNode)entity;
            return node.getChildren().toArray();
        }
        throw new RuntimeException( "Type not supported" );
    }
}
