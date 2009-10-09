// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.projectsettings;

import java.io.InputStream;
import java.util.Properties;

public class SettingsLoader {

    private static final String CONFIG_COMPILER_DEFAULTS_PROPERTIES = "config/compiler_defaults.properties";

    public Properties getCompilerWarningsDefaults() {
        return getDefaultsFrom( CONFIG_COMPILER_DEFAULTS_PROPERTIES );
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
