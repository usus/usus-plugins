package org.projectusus.ui.colors;

import static org.junit.Assert.assertEquals;
import static org.projectusus.ui.colors.UsusColors.getSharedColors;

import org.eclipse.swt.graphics.Color;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
// TODO aOSD Erweitern/Reparieren
public class UsusColorsTest {

    @Test
    public void toHueIsBetween0And360() {
        assertEquals( 0, getSharedColors().toHue( Integer.MIN_VALUE ) );
        assertEquals( 90, getSharedColors().toHue( Integer.MIN_VALUE / 2 ) );
        assertEquals( 180, getSharedColors().toHue( 0 ) );
        assertEquals( 270, getSharedColors().toHue( Integer.MAX_VALUE / 2 ) );
        assertEquals( 360, getSharedColors().toHue( Integer.MAX_VALUE ) );
    }

    @Test
    public void adjustBrightness() {
        Color black = getSharedColors().adjustSaturation( UsusColors.DARK_RED, 0 );

        assertEquals( 0.0, black.getRGB().getHSB()[2], 0.01 );

        black = getSharedColors().adjustSaturation( UsusColors.DARK_RED, 20 );

        assertEquals( 0.0, black.getRGB().getHSB()[2], 0.01 );
    }

}
