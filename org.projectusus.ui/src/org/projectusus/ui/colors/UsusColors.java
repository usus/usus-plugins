// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.colors;

import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class UsusColors {

    public static final String RED = "RED"; //$NON-NLS-1$
    public static final String BLACK = "BLACK"; //$NON-NLS-1$
    public static final String PACKAGE0 = "PACKAGE0"; //$NON-NLS-1$
    public static final String PACKAGE1 = "PACKAGE1"; //$NON-NLS-1$
    public static final String PACKAGE2 = "PACKAGE2"; //$NON-NLS-1$
    public static final String PACKAGE3 = "PACKAGE3"; //$NON-NLS-1$
    public static final String PACKAGE4 = "PACKAGE4"; //$NON-NLS-1$
    public static final String PACKAGE5 = "PACKAGE5"; //$NON-NLS-1$
    public static final String PACKAGE6 = "PACKAGE6"; //$NON-NLS-1$
    public static final String PACKAGE7 = "PACKAGE7"; //$NON-NLS-1$
    public static final String PACKAGE8 = "PACKAGE8"; //$NON-NLS-1$
    public static final String PACKAGE9 = "PACKAGE9"; //$NON-NLS-1$
    public static final String PACKAGE10 = "PACKAGE10"; //$NON-NLS-1$
    public static final String PACKAGE11 = "PACKAGE11"; //$NON-NLS-1$
    public static final String PACKAGE12 = "PACKAGE12"; //$NON-NLS-1$
    public static final String PACKAGE13 = "PACKAGE13"; //$NON-NLS-1$
    public static final String PACKAGE14 = "PACKAGE14"; //$NON-NLS-1$
    public static final String PACKAGE15 = "PACKAGE15"; //$NON-NLS-1$

    private static final UsusColors _instance = new UsusColors();
    private ColorRegistry colorRegistry;

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
        declare( PACKAGE0, new RGB( 0xFF, 0xFF, 0xFF ) ); // WEISS
        declare( PACKAGE1, new RGB( 0xFF, 0xCC, 0x5A ) ); // NETZMELONE
        declare( PACKAGE2, new RGB( 0xB7, 0xFF, 0x58 ) ); // ZUCKERMELONE
        declare( PACKAGE3, new RGB( 0x00, 0xFF, 0xCB ) ); // MEERESGRUEN
        declare( PACKAGE4, new RGB( 0x28, 0xCE, 0xFF ) ); // HIMMEL
        declare( PACKAGE5, new RGB( 0xE7, 0x59, 0xFF ) ); // LAVENDEL
        declare( PACKAGE6, new RGB( 0xFF, 0x61, 0xD1 ) ); // NELKE
        declare( PACKAGE7, new RGB( 0xFF, 0x5B, 0x61 ) ); // LACHS
        declare( PACKAGE8, new RGB( 0xFF, 0xFF, 0xB2 ) ); // Blassgelb
        declare( PACKAGE9, new RGB( 0x00, 0xFF, 0x5B ) ); // PFLANZEN
        declare( PACKAGE10, new RGB( 0x88, 0xE7, 0xFF ) ); // Blasses Tuerkis
        declare( PACKAGE11, new RGB( 0x00, 0x7E, 0xFF ) ); // WASSER
        declare( PACKAGE12, new RGB( 0xFF, 0xA4, 0xC9 ) ); // Rosa
        declare( PACKAGE13, new RGB( 0xFF, 0x7A, 0x00 ) ); // MANDARINE
        declare( PACKAGE14, new RGB( 0xAA, 0xAA, 0xAA ) ); // 
        declare( PACKAGE15, new RGB( 0xCC, 0xCC, 0xCC ) ); // SILBER
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
