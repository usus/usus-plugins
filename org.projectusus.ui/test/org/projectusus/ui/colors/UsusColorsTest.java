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

        float newSaturation = hsbNewColor[1];
        assertEquals( newSaturation, 0.5, 0 );
        assertEquals( hsbNewColor[0], hsbDarkRed[0], 0 );
        assertEquals( hsbNewColor[2], hsbDarkRed[2], 0 );
    }

    @Test
    public void adjustSaturationWithMinimum() {
        Color color = getSharedColors().adjustSaturation( UsusColors.DARK_RED, 0.05f );
        float saturation = color.getRGB().getHSB()[1];
        assertEquals( saturation, MIN_SATURATION, 0.01 );
    }
}
