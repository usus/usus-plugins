package org.projectusus.ui.dependencygraph.nodes;

import java.util.Set;

import org.eclipse.swt.graphics.Color;

public interface IEdgeColorProvider {

    Color getEdgeColor( Object src, Object dst, boolean highlightStrongConnections );

    void recalculateColors( Set<GraphNode> visibleNodes );
}
