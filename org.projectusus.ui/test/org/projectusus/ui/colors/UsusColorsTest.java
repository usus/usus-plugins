package org.projectusus.ui.colors;

import static org.junit.Assert.assertEquals;
import static org.projectusus.ui.colors.UsusColors.MINIMUM_SATURATION;
import static org.projectusus.ui.colors.UsusColors.getSharedColors;

import org.eclipse.swt.graphics.Color;
import org.junit.Test;

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
    public void adjustSaturationBetweenMinAndMax() {
        Color starkRed = getSharedColors().adjustSaturation( UsusColors.DARK_RED, 1 );
        assertEquals( 1, getSaturation( starkRed ), 0.01 );

        Color fadingRed = getSharedColors().adjustSaturation( UsusColors.DARK_RED, 0 );
        assertEquals( MINIMUM_SATURATION, getSaturation( fadingRed ), 0.01 );

        Color mediumRed = getSharedColors().adjustSaturation( UsusColors.DARK_RED, 0.6f );
        assertEquals( 0.6, getSaturation( mediumRed ), 0.01 );
    }

    @Test( expected = IllegalArgumentException.class )
    public void saturationMustNotBeGreaterThanOne() {
        getSharedColors().adjustSaturation( UsusColors.BLACK, 1.1f );
    }

    private float getSaturation( Color color ) {
        return color.getRGB().getHSB()[1];
    }

}
