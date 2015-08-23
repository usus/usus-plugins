package org.projectusus.ui.dependencygraph.nodes;

import java.util.Set;

import org.eclipse.swt.graphics.Color;
import org.projectusus.core.filerelations.model.Packagename;

public interface IEdgeColorProvider {

    Color getEdgeColor( Object src, Object dst, boolean highlightStrongConnections );

    void recalculateColors( Set<Packagename> visibleNodes );
}
