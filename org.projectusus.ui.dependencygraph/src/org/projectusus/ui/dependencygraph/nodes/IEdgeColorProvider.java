package org.projectusus.ui.dependencygraph.nodes;

import org.eclipse.swt.graphics.Color;

public interface IEdgeColorProvider {

    Color getEdgeColor( Object src, Object dst, boolean hightlightStrongConnections );

    void refresh();
}
