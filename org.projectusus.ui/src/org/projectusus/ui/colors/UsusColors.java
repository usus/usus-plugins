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

    public static final String RED = "RED"; //$NON-NLS-1$
    public static final String BLACK = "BLACK"; //$NON-NLS-1$

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
        declare( RED, new RGB( 220, 60, 60 ) );
        declare( BLACK, new RGB( 100, 100, 100 ) );
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

    public Color getNodeColorFor( int hue ) {
        String symbolicName = "NODE_COLOR_HUE_" + hue; //$NON-NLS-1$
        ColorRegistry registry = getColorRegistry();
        if( !registry.hasValueFor( symbolicName ) ) {
            float saturation = random.nextFloat() / 2;
            float brightness = 1 - random.nextFloat() / 5;
            registry.put( symbolicName, new RGB( hue, saturation, brightness ) );
        }
        return registry.get( symbolicName );
    }
}
