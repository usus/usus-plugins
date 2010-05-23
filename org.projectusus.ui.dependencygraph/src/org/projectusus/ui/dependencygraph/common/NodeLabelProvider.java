package org.projectusus.ui.dependencygraph.common;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.zest.core.viewers.EntityConnectionData;
import org.projectusus.core.basis.GraphNode;

public class NodeLabelProvider extends LabelProvider {
    @Override
    public String getText( Object element ) {
        if( element instanceof GraphNode ) {
            GraphNode node = (GraphNode)element;
            return node.getNodeName();
        }
        if( element instanceof EntityConnectionData ) {
            EntityConnectionData data = (EntityConnectionData)element;
            GraphNode dest = (GraphNode)data.dest;
            return dest.getEdgeEndLabel();
        }
        throw new RuntimeException( "Type not supported: " + element.getClass().toString() );
    }
}
