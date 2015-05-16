package org.projectusus.ui.colors;

import org.eclipse.swt.graphics.Color;
import org.projectusus.core.filerelations.model.PackageRelations;

public class EdgeColorProvider {

    private static int threshold = 0;
    private final PackageRelations packageRelations;

    public EdgeColorProvider( PackageRelations packageRelations ) {
        this.packageRelations = packageRelations;
    }

    public Color getEdgeColor( int linkCount ) {
        float maxWert = packageRelations.getMaxLinkCount();
        // float scaledCount = Math.min( maxWert, linkCount );
        float brightness = 1 - (linkCount / maxWert);
        return UsusColors.getSharedColors().adjustBrightness( UsusColors.DARK_RED, brightness );
    }

    public static void setThreshold( int threshold ) {
        // TODO aOSD Sieht komisch aus
        EdgeColorProvider.threshold = threshold;
    }

}
