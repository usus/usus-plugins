package org.projectusus.ui.dependencygraph.colorProvider;

import static org.projectusus.ui.colors.UsusColors.DARK_RED;
import static org.projectusus.ui.colors.UsusColors.getSharedColors;

import org.eclipse.swt.graphics.Color;
import org.projectusus.core.filerelations.model.PackageRelations;
import org.projectusus.ui.dependencygraph.nodes.IEdgeColorProvider;
import org.projectusus.ui.dependencygraph.nodes.PackageRepresenter;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

public class PackageEdgeColorProvider implements IEdgeColorProvider {

    private Supplier<PackageRelations> packageRelationsSupplier;

    public PackageEdgeColorProvider() {
        initPackageRelations();
    }

    public Color getEdgeColor( Object src, Object dest, boolean highlightStrongConnections ) {
        if( highlightStrongConnections ) {
            float saturation = computeSaturation( src, dest );
            return getSharedColors().adjustSaturation( DARK_RED, saturation );
        }
        return getSharedColors().getColor( DARK_RED );
    }

    private float computeSaturation( Object src, Object dest ) {
        PackageRelations packageRelations = packageRelationsSupplier.get();

        int crossLinkCount = getCrossLinkCount( (PackageRepresenter)src, (PackageRepresenter)dest, packageRelations );
        float maxCrossLinkCount = packageRelations.getMaxCrossLinkCount();
        float saturation = crossLinkCount / maxCrossLinkCount;
        return saturation;
    }

    private int getCrossLinkCount( PackageRepresenter src, PackageRepresenter target, PackageRelations packageRelations ) {
        return packageRelations.getCrossLinkCount( src.getPackagename(), target.getPackagename() );
    }

    public void refresh() {
        initPackageRelations();
    }

    private void initPackageRelations() {
        packageRelationsSupplier = Suppliers.memoize( new Supplier<PackageRelations>() {

            public PackageRelations get() {
                return new PackageRelations();
            }
        } );
    }
}
