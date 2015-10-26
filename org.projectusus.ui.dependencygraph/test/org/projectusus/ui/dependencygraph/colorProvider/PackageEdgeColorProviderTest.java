package org.projectusus.ui.dependencygraph.colorProvider;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.projectusus.ui.colors.UsusColors.getSharedColors;

import org.eclipse.core.resources.IFile;
import org.eclipse.swt.graphics.Color;
import org.junit.Before;
import org.junit.Test;
import org.projectusus.core.filerelations.model.ClassDescriptor;
import org.projectusus.core.filerelations.model.Classname;
import org.projectusus.core.filerelations.model.Packagename;
import org.projectusus.core.statistics.UsusModelProvider;
import org.projectusus.ui.colors.UsusColors;
import org.projectusus.ui.dependencygraph.nodes.PackageRepresenter;

public class PackageEdgeColorProviderTest {

    PackageEdgeColorProvider provider = new PackageEdgeColorProvider();

    private static Packagename SRC = Packagename.of( "SRC", null ); //$NON-NLS-1$
    private static Packagename DEST = Packagename.of( "DEST", null ); //$NON-NLS-1$
    private static Packagename OTHER_PACKAGE = Packagename.of( "OTHER_PACKAGE", null ); //$NON-NLS-1$

    private ClassDescriptor SRC_A;
    private ClassDescriptor DEST_A;
    private ClassDescriptor OTHER_PACKAGE_A;
    private ClassDescriptor OTHER_PACKAGE_B;

    @Before
    public void setup() {
        UsusModelProvider.clear();
        SRC_A = createDescriptor( SRC );
        DEST_A = createDescriptor( DEST );
        OTHER_PACKAGE_A = createDescriptor( OTHER_PACKAGE );
        OTHER_PACKAGE_B = createDescriptor( OTHER_PACKAGE );
    }

    @Test
    public void edgeColorRedWithOneEdge() {
        SRC_A.addChild( DEST_A );
        Color edgeColor = provider.getEdgeColor( new PackageRepresenter( SRC ), new PackageRepresenter( DEST ), true );

        assertThat( edgeColor, is( getSharedColors().adjustSaturation( UsusColors.DARK_RED, 1 ) ) );
    }

    @Test
    public void edgeColorBrightenedRedWithCrossPackageRelations() {
        SRC_A.addChild( DEST_A );
        DEST_A.addChild( OTHER_PACKAGE_A );
        DEST_A.addChild( OTHER_PACKAGE_B );
        Color edgeColor = provider.getEdgeColor( new PackageRepresenter( SRC ), new PackageRepresenter( DEST ), true );

        assertThat( edgeColor, is( getSharedColors().adjustSaturation( UsusColors.DARK_RED, 0.5f ) ) );
    }

    @Test
    public void edgeColorRedWithoutHighlightingStrongConnections() {
        SRC_A.addChild( DEST_A );
        DEST_A.addChild( OTHER_PACKAGE_A );
        DEST_A.addChild( OTHER_PACKAGE_B );
        Color edgeColor = provider.getEdgeColor( new PackageRepresenter( SRC ), new PackageRepresenter( DEST ), false );

        assertThat( edgeColor, is( getSharedColors().getColor( UsusColors.DARK_RED ) ) );
    }

    private static ClassDescriptor createDescriptor( Packagename packagename ) {
        return ClassDescriptor.of( mock( IFile.class ), new Classname( "classname1" ), packagename ); //$NON-NLS-1$
    }
}
