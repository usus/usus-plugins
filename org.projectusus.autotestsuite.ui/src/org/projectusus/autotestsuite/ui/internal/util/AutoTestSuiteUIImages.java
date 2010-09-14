// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.autotestsuite.ui.internal.util;

import static org.projectusus.autotestsuite.AutoTestSuitePlugin.log;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.Bundle;
import org.projectusus.autotestsuite.AutoTestSuitePlugin;

public class AutoTestSuiteUIImages implements ISharedAutoTestSuiteImages {

    private static URL baseUrl;
    private static final ISharedAutoTestSuiteImages _instance = new AutoTestSuiteUIImages();
    private ImageRegistry imageRegistry;

    static {
        String pathPrefix = "icons/";
        try {
            Bundle bundle = AutoTestSuitePlugin.getDefault().getBundle();
            baseUrl = new URL( bundle.getEntry( "/" ), pathPrefix );
        } catch( MalformedURLException malfux ) {
            // do nothing
        }
    }

    private final static String OBJECT = "obj16/"; // basic colors - size 16x16

    private AutoTestSuiteUIImages() {
        // no instantiation
    }

    public static ISharedAutoTestSuiteImages getSharedImages() {
        return _instance;
    }

    public Image getImage( String key ) {
        return getImageRegistry().get( key );
    }

    public ImageDescriptor getDescriptor( String key ) {
        return getImageRegistry().getDescriptor( key );
    }

    private void declareImages() {
        declare( OBJ_JUNIT_3, OBJECT + "junit3.gif" );
        declare( OBJ_JUNIT_4, OBJECT + "junit4.gif" );
        declare( OBJ_TAB, OBJECT + "tab.gif" );
    }

    private void declare( final String key, final String path ) {
        ImageDescriptor desc = ImageDescriptor.getMissingImageDescriptor();
        try {
            desc = ImageDescriptor.createFromURL( makeIconFileURL( path ) );
        } catch( MalformedURLException exception ) {
            log( exception );
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

}
