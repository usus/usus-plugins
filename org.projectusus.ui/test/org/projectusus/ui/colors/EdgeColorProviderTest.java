package org.projectusus.ui.colors;

import static java.lang.Double.valueOf;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.eclipse.swt.graphics.Color;
import org.junit.Ignore;
import org.junit.Test;
import org.projectusus.core.filerelations.model.PackageRelations;

@Ignore
// TODO aOSD Erweitern/Reparieren
public class EdgeColorProviderTest {

    EdgeColorProvider provider = new EdgeColorProvider( new PackageRelations() );

    @Test
    public void oneLinkResultsInBrightRed() {
        Color brightRed = provider.getEdgeColor( 1 );

        assertThat( valueOf( brightRed.getRGB().getHSB()[2] ), is( closeTo( 0.9, 0.1 ) ) );

    }

    @Test
    public void twentyResultsInBlack() {
        Color black = provider.getEdgeColor( 20 );

        assertThat( Float.valueOf( black.getRGB().getHSB()[2] ), is( Float.valueOf( 0.0f ) ) );
    }
}
