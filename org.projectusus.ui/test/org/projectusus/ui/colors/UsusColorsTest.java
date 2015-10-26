package org.projectusus.ui.colors;

import static org.junit.Assert.assertEquals;
import static org.projectusus.ui.colors.UsusColors.MIN_SATURATION;
import static org.projectusus.ui.colors.UsusColors.getSharedColors;

import org.eclipse.swt.graphics.Color;
import org.junit.Test;

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
    public void onlyAdjustTheSaturation() {
        Color darkRed = getSharedColors().getColor( UsusColors.DARK_RED );
        float[] hsbDarkRed = darkRed.getRGB().getHSB();

        Color newColor = getSharedColors().adjustSaturation( UsusColors.DARK_RED, 0.5f );
        float[] hsbNewColor = newColor.getRGB().getHSB();

        assertEquals( getSaturation( newColor ), 0.5, 0 );
        assertEquals( hsbNewColor[0], hsbDarkRed[0], 0 );
        assertEquals( hsbNewColor[2], hsbDarkRed[2], 0 );
    }

    @Test
    public void adjustSaturationBetweenMinAndMax() {
        Color starkRed = getSharedColors().adjustSaturation( UsusColors.DARK_RED, 1 );
        assertEquals( 1, getSaturation( starkRed ), 0.01 );

        Color fadingRed = getSharedColors().adjustSaturation( UsusColors.DARK_RED, 0 );
        assertEquals( MIN_SATURATION, getSaturation( fadingRed ), 0.01 );

        Color mediumRed = getSharedColors().adjustSaturation( UsusColors.DARK_RED, 0.6f );
        assertEquals( 0.6, getSaturation( mediumRed ), 0.01 );
    }

    @Test( expected = IllegalArgumentException.class )
    public void saturationMustNotBeGreaterThanOne() {
        getSharedColors().adjustSaturation( UsusColors.BLACK, 1.1f );
    }

    @Test
    public void saturationIsRoundedToTwoDigitsAfterDecimalPoint() {
        float saturation = getSharedColors().restrictSaturation( 0.449f );
        assertEquals( 0.45f, saturation, 0.0f );

        saturation = getSharedColors().restrictSaturation( 0.45f );
        assertEquals( 0.45f, saturation, 0.0f );

        saturation = getSharedColors().restrictSaturation( 0.45454545f );
        assertEquals( 0.45f, saturation, 0.0f );
    }

    private float getSaturation( Color color ) {
        return color.getRGB().getHSB()[1];
    }
}
