package org.projectusus.ui.dependencygraph.colorProvider;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
// TODO aOSD Erweitern/Reparieren
public class PackageEdgeColorProviderTest {

    PackageEdgeColorProvider provider = new PackageEdgeColorProvider();

    @Test
    public void oneLinkResultsInBrightRed() {
        // Color brightRed = provider.getEdgeColor( 1 );

        // assertThat( valueOf( brightRed.getRGB().getHSB()[2] ), is( closeTo( 0.9, 0.1 ) ) );

    }

    @Test
    public void twentyResultsInBlack() {
        // Color black = provider.getEdgeColor( 20 );

        // assertThat( Float.valueOf( black.getRGB().getHSB()[2] ), is( Float.valueOf( 0.0f ) ) );
    }
}
