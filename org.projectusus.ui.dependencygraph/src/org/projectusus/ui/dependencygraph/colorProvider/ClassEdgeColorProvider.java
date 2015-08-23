package org.projectusus.ui.dependencygraph.colorProvider;

import static org.projectusus.ui.colors.UsusColors.DARK_GREY;
import static org.projectusus.ui.colors.UsusColors.DARK_RED;
import static org.projectusus.ui.colors.UsusColors.getSharedColors;
import static org.projectusus.ui.dependencygraph.nodes.NodeLabelProvider.isCrossPackageRelation;

import java.util.Set;

import org.eclipse.swt.graphics.Color;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.ui.dependencygraph.nodes.IEdgeColorProvider;

public class ClassEdgeColorProvider implements IEdgeColorProvider {

    public Color getEdgeColor( Object src, Object dest, boolean highlightStrongConnections ) {
        if( isCrossPackageRelation( src, dest ) ) {
            return getSharedColors().getColor( DARK_RED );
        }
        return getSharedColors().getColor( DARK_GREY );
    }

    public void recalculateColors( Set<Packagename> visibleNodes ) {
        // nothing to refresh
    }
}
