// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.projectsettings.core;

import static org.projectusus.projectsettings.internal.util.CoreTexts.projectSettings_defaultName;

import java.io.InputStream;
import java.util.Properties;

public class UsusStandardSettingsLoader implements SettingsProvider {

    private static final String CONFIG_COMPILER_DEFAULTS_PROPERTIES = "config/compiler_defaults.properties"; //$NON-NLS-1$

    public ProjectSettings getUsusProjectSettings() {
        ProjectSettings result = new ProjectSettings( projectSettings_defaultName );
        result.getCompilerwarningSettings().loadValuesFromProperties( getDefaultsFrom( CONFIG_COMPILER_DEFAULTS_PROPERTIES ) );
        return result;
    }

    private Properties getDefaultsFrom( String fileName ) {
        Properties properties = new Properties();
        try {
            InputStream inStream = getClass().getClassLoader().getResourceAsStream( fileName );
            properties.load( inStream );
        } catch( Exception exception ) {
            exception.printStackTrace();
        }
        return properties;
    }

}
