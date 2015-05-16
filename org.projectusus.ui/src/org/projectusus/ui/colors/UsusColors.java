// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.colors;

import java.util.Random;

import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class UsusColors {

    public static final String DARK_RED = "DARK_RED"; //$NON-NLS-1$
    public static final String DARK_GREY = "DARK_GREY"; //$NON-NLS-1$
    public static final String BLACK = "BLACK"; //$NON-NLS-1$
    public static final String WHITE = "WHITE"; //$NON-NLS-1$
    public static final String USUS_LIGHT_BLUE = "USUS_LIGHT_BLUE"; //$NON-NLS-1$

    private static final UsusColors _instance = new UsusColors();
    private ColorRegistry colorRegistry;
    private final Random random = new Random( 1 );

    private UsusColors() {
        // no instantiation
    }

    public static UsusColors getSharedColors() {
        return _instance;
    }

    public Color getColor( final String key ) {
        return getColorRegistry().get( key );
    }

    private void declareColors() {
        declare( DARK_RED, new RGB( 220, 60, 60 ) );
        declare( DARK_GREY, new RGB( 100, 100, 100 ) );
        declare( BLACK, new RGB( 0, 0, 0 ) );
        declare( WHITE, new RGB( 255, 255, 255 ) );
        declare( USUS_LIGHT_BLUE, new RGB( 0, 122, 197 ) );
    }

    private void declare( String key, RGB rgb ) {
        colorRegistry.put( key, rgb );
    }

    private ColorRegistry getColorRegistry() {
        if( colorRegistry == null ) {
            initializeColorRegistry();
        }
        return colorRegistry;
    }

    private ColorRegistry initializeColorRegistry() {
        colorRegistry = new ColorRegistry( Display.getDefault() );
        declareColors();
        return colorRegistry;
    }

    public Color getNodeColorFor( int someIntValue ) {
        String symbolicName = "NODE_COLOR_" + someIntValue; //$NON-NLS-1$
        ColorRegistry registry = getColorRegistry();
        if( !registry.hasValueFor( symbolicName ) ) {
            int hue = toHue( someIntValue );
            float saturation = 0.1f + random.nextFloat() / 1.25f;
            float brightness = 0.8f + random.nextFloat() / 5;
            registry.put( symbolicName, new RGB( hue, saturation, brightness ) );
        }
        return registry.get( symbolicName );
    }

    int toHue( int value ) {
        long positive = ((long)value - Integer.MIN_VALUE) / 2;
        double y = (double)positive / Integer.MAX_VALUE;
        return (int)Math.round( 360 * y );
    }

    // TODO aOSD Parameter besser benennen. Zuerst get auf Registry, put falls nicht vorhanden
    public Color adjustBrightness( String colorKey, float brightness ) {
        Color color = getColor( colorKey );
        float[] hsb = color.getRGB().getHSB();
        ColorRegistry registry = getColorRegistry();
        String name = colorKey + brightness;
        registry.put( name, new RGB( hsb[0], Math.max( 0.2f, 1 - brightness ), hsb[2] ) );
        return registry.get( name );
    }

}
