// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.colors;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.Bundle;
import org.projectusus.core.util.Defect;
import org.projectusus.ui.internal.UsusUIPlugin;

public class UsusUIImages implements ISharedUsusImages {

    private static URL baseUrl;
    private static final ISharedUsusImages _instance = new UsusUIImages();
    private ImageRegistry imageRegistry;

    static {
        String pathPrefix = "icons/full/"; //$NON-NLS-1$
        try {
            Bundle bundle = UsusUIPlugin.getDefault().getBundle();
            baseUrl = new URL( bundle.getEntry( "/" ), pathPrefix ); //$NON-NLS-1$
        } catch( MalformedURLException malfux ) {
            throw new Defect( malfux );
        }
    }

    private final static String OBJECT = "obj16/"; // basic colors - size 16x16 //$NON-NLS-1$
    private final static String VIEW = "eview16/"; // basic colors - size 16x16 //$NON-NLS-1$

    private UsusUIImages() {
        // no instantiation
    }

    public static ISharedUsusImages getSharedImages() {
        return _instance;
    }

    public Image getImage( String key ) {
        return getImageRegistry().get( key );
    }

    public ImageDescriptor getDescriptor( String key ) {
        return getImageRegistry().getDescriptor( key );
    }

    private void declareImages() {
        declare( OBJ_CODE_PROPORTIONS, OBJECT + "codeproportions.gif" ); //$NON-NLS-1$
        declare( OBJ_INFO, OBJECT + "info.gif" ); //$NON-NLS-1$
        declare( OBJ_WARNINGS, OBJECT + "warnings.gif" ); //$NON-NLS-1$
        declare( VIEW_WARNING, VIEW + "warning.gif" ); //$NON-NLS-1$
        declare( OBJ_LEVELUP, OBJECT + "levelup.gif" ); //$NON-NLS-1$
        declare( OBJ_LEVELDOWN, OBJECT + "leveldown.gif" ); //$NON-NLS-1$
        declare( OBJ_NEW, OBJECT + "new.gif" ); //$NON-NLS-1$
        declare( OBJ_DELETED, OBJECT + "deleted.gif" ); //$NON-NLS-1$
    }

    private void declare( final String key, final String path ) {
        ImageDescriptor desc = ImageDescriptor.getMissingImageDescriptor();
        try {
            desc = ImageDescriptor.createFromURL( makeIconFileURL( path ) );
        } catch( MalformedURLException malfux ) {
            UsusUIPlugin.getDefault().log( malfux );
        }
        imageRegistry.put( key, desc );
    }

    private ImageRegistry getImageRegistry() {
        if( imageRegistry == null ) {
            initializeImageRegistry();
        }
        return imageRegistry;
    }

    private ImageRegistry initializeImageRegistry() {
        imageRegistry = new ImageRegistry( Display.getDefault() );
        declareImages();
        return imageRegistry;
    }

    private URL makeIconFileURL( final String iconPath ) throws MalformedURLException {
        if( baseUrl == null ) {
            throw new MalformedURLException();
        }
        return new URL( baseUrl, iconPath );
    }

    public Image getTrendImage( int trend ) {
        if( trend == 0 ) {
            return null;
        }
        return getImage( trend > 0 ? OBJ_LEVELDOWN : OBJ_LEVELUP );
    }

    public Image getTrendImage( int oldMetricsValue, int currentMetricsValue ) {
        if( oldMetricsValue == 0 ) {
            return getImage( OBJ_NEW );
        }
        if( currentMetricsValue == 0 ) {
            return getImage( OBJ_DELETED );
        }
        if( oldMetricsValue == currentMetricsValue ) {
            return null;
        }
        return getImage( currentMetricsValue > oldMetricsValue ? OBJ_LEVELDOWN : OBJ_LEVELUP );
    }

}
