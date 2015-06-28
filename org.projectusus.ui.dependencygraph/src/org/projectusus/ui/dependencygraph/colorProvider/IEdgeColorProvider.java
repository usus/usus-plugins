package org.projectusus.ui.dependencygraph.colorProvider;

import org.eclipse.swt.graphics.Color;

public interface IEdgeColorProvider {

    Color getEdgeColor( Object src, Object dst, boolean hightlightStrongConnections );

    void refresh();
}
