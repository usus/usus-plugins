package org.projectusus.ui.dependencygraph.colorProvider;

import static org.projectusus.ui.colors.UsusColors.DARK_RED;
import static org.projectusus.ui.colors.UsusColors.getSharedColors;

import org.eclipse.swt.graphics.Color;
import org.projectusus.core.filerelations.model.PackageRelations;
import org.projectusus.ui.colors.UsusColors;
import org.projectusus.ui.dependencygraph.nodes.PackageRepresenter;

public class PackageEdgeColorProvider implements IEdgeColorProvider {

    private PackageRelations packageRelations;

    public PackageEdgeColorProvider() {
        setPackageRelations();
    }

    public Color getEdgeColor( Object src, Object dest, boolean hightlightStrongConnections ) {
        if( hightlightStrongConnections ) {
            // REVIEW aOSD müssen die Package relations gecached werden?
            int crossLinkCount = getCrossLinkCount( (PackageRepresenter)src, (PackageRepresenter)dest, packageRelations );

            float maxCrossLinkCount = packageRelations.getMaxLinkCount();
            float brightness = 1 - (crossLinkCount / maxCrossLinkCount);
            return getSharedColors().adjustBrightness( UsusColors.DARK_RED, brightness );
        }
        return getSharedColors().getColor( DARK_RED );
    }

    private int getCrossLinkCount( PackageRepresenter src, PackageRepresenter target, PackageRelations packageRelations ) {
        return packageRelations.getCrossLinkCount( src.getPackagename(), target.getPackagename() );
    }

    public void refresh() {
        setPackageRelations();
    }

    private void setPackageRelations() {
        packageRelations = new PackageRelations();
    }
}
