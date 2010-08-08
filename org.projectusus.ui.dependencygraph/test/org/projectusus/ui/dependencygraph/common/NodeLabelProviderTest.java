package org.projectusus.ui.dependencygraph.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NodeLabelProviderTest {

    private final NodeLabelProvider provider = new NodeLabelProvider();

    @Test
    public void toHueIsBetween0And360() {
        assertEquals( 0, provider.toHue( Integer.MIN_VALUE ) );
        assertEquals( 90, provider.toHue( Integer.MIN_VALUE / 2 ) );
        assertEquals( 180, provider.toHue( 0 ) );
        assertEquals( 270, provider.toHue( Integer.MAX_VALUE / 2 ) );
        assertEquals( 360, provider.toHue( Integer.MAX_VALUE ) );
    }

}
