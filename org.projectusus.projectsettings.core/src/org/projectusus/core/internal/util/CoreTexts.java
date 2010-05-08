// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.util;

import org.eclipse.osgi.util.NLS;

public final class CoreTexts extends NLS {

    public static String projectSettings_defaultName;
    public static String projectSettings_settingsName;

    public static String SettingsAccess_load_settings;
    public static String SettingsAccess_save_settings;

    private static final String BUNDLE_NAME = CoreTexts.class.getPackage().getName() + ".coretexts"; //$NON-NLS-1$

    static {
        NLS.initializeMessages( BUNDLE_NAME, CoreTexts.class );
    }
}
