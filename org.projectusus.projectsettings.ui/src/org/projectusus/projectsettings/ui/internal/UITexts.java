// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.projectsettings.ui.internal;

import org.eclipse.osgi.util.NLS;

public final class UITexts extends NLS {

    public static String SelectProject_title;
    public static String SelectProjectPage_description;
    public static String SelectSettingPage_available_setting;
    public static String SelectSettingPage_description;
    public static String SelectSettingPage_title;

    private static final String BUNDLE_NAME = UITexts.class.getPackage().getName() + ".uitexts"; //$NON-NLS-1$

    static {
        NLS.initializeMessages( BUNDLE_NAME, UITexts.class );
    }
}
