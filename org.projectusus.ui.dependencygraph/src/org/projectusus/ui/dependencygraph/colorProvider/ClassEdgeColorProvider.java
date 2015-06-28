package org.projectusus.ui.dependencygraph.colorProvider;

import static org.projectusus.ui.colors.UsusColors.DARK_GREY;
import static org.projectusus.ui.colors.UsusColors.DARK_RED;
import static org.projectusus.ui.colors.UsusColors.getSharedColors;

import org.eclipse.swt.graphics.Color;
import org.projectusus.ui.dependencygraph.nodes.GraphNode;

public class ClassEdgeColorProvider implements IEdgeColorProvider {

    public Color getEdgeColor( Object src, Object dest, boolean hightlightStrongConnections ) {
        if( areClassesInDifferentPackages( src, dest ) ) {
            return getSharedColors().getColor( DARK_RED );
        }
        return getSharedColors().getColor( DARK_GREY );
    }

    // TODO aOSD kopiert von NodeLabelProvider
    private boolean areClassesInDifferentPackages( Object src, Object dest ) {
        if( (src instanceof GraphNode) && dest instanceof GraphNode ) {
            return ((GraphNode)src).isInDifferentPackageThan( (GraphNode)dest );
        }
        return false;
    }

    public void refresh() {
        // nothing to refresh
    }
}
