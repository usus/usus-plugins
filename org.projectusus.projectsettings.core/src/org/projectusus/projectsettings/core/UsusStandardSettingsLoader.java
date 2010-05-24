// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.projectsettings.core;

import static org.projectusus.projectsettings.internal.util.CoreTexts.projectSettings_defaultName;

import java.io.IOException;
import java.util.Properties;

import org.projectusus.projectsettings.internal.util.CoreTexts;

public class UsusStandardSettingsLoader implements SettingsProvider {

    private static final String CONFIG_COMPILER_DEFAULTS_PROPERTIES = "config/compiler_defaults.properties"; //$NON-NLS-1$

    public Preferences getUsusProjectSettings() {
        return new Preferences( projectSettings_defaultName, getDefaultsFrom( CONFIG_COMPILER_DEFAULTS_PROPERTIES ) );
    }

    private Properties getDefaultsFrom( String fileName ) {
        Properties properties = new Properties();
        try {
            properties.load( getClass().getClassLoader().getResourceAsStream( fileName ) );
        } catch( IOException exception ) {
            throw new RuntimeException( CoreTexts.Error_load_settings, exception );
        }
        return properties;
    }

}
