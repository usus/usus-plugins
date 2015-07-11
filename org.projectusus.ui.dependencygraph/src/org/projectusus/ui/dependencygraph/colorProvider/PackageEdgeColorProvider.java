package org.projectusus.ui.dependencygraph.colorProvider;

import static org.projectusus.ui.colors.UsusColors.DARK_RED;
import static org.projectusus.ui.colors.UsusColors.getSharedColors;

import org.eclipse.swt.graphics.Color;
import org.projectusus.core.filerelations.model.PackageRelations;
import org.projectusus.ui.colors.UsusColors;
import org.projectusus.ui.dependencygraph.nodes.IEdgeColorProvider;
import org.projectusus.ui.dependencygraph.nodes.PackageRepresenter;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

public class PackageEdgeColorProvider implements IEdgeColorProvider {

    private Supplier<PackageRelations> packageRelationsSupplier;

    public PackageEdgeColorProvider() {
        setPackageRelations();
    }

    public Color getEdgeColor( Object src, Object dest, boolean hightlightStrongConnections ) {
        if( hightlightStrongConnections ) {
            PackageRelations packageRelations = packageRelationsSupplier.get();

            int crossLinkCount = getCrossLinkCount( (PackageRepresenter)src, (PackageRepresenter)dest, packageRelations );
            float maxCrossLinkCount = packageRelations.getMaxCrossLinkCount();
            float saturation = crossLinkCount / maxCrossLinkCount;

            return getSharedColors().adjustSaturation( UsusColors.DARK_RED, saturation );
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
        packageRelationsSupplier = Suppliers.memoize( new Supplier<PackageRelations>() {

            public PackageRelations get() {
                return new PackageRelations();
            }
        } );
    }
}
