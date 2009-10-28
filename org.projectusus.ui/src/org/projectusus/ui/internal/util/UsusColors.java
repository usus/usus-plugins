// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.util;

import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class UsusColors implements ISharedUsusColors {

    private static final ISharedUsusColors _instance = new UsusColors();
    private ColorRegistry colorRegistry;

    private UsusColors() {
        // no instantiation
    }

    public static ISharedUsusColors getSharedColors() {
        return _instance;
    }

    public Color getColor( final String key ) {
        return getColorRegistry().get( key );
    }

    private void declareColors() {
        declare( ISIS_METRIC_PC, new RGB( 210, 160, 0 ) );
        declare( ISIS_METRIC_ACD, new RGB( 240, 210, 40 ) );
        declare( ISIS_METRIC_TA, new RGB( 50, 156, 28 ) );
        declare( ISIS_METRIC_KG, new RGB( 100, 210, 60 ) );
        declare( ISIS_METRIC_CC, new RGB( 10, 95, 150 ) );
        declare( ISIS_METRIC_ML, new RGB( 60, 125, 160 ) );
        declare( ISIS_METRIC_CW, new RGB( 100, 168, 200 ) );
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
}
