// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.projectsettings;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

public class SettingsProviderExtension {

    private static final String EXTENSION_POINT_ID = "org.projectusus.core.settingsprovider"; //$NON-NLS-1$

    public List<SettingsProvider> loadSettingsProvider() {
        List<SettingsProvider> result = new ArrayList<SettingsProvider>();
        IConfigurationElement[] configurationElements = Platform.getExtensionRegistry().getConfigurationElementsFor( EXTENSION_POINT_ID );
        for( IConfigurationElement configurationElement : configurationElements ) {
            SettingsProvider settingsProvider = getSettingsProvider( configurationElement );
            if( settingsProvider != null ) {
                result.add( settingsProvider );
            }
        }
        return result;
    }

    private SettingsProvider getSettingsProvider( IConfigurationElement configurationElement ) {
        try {
            Object object = configurationElement.createExecutableExtension( "clazz" ); //$NON-NLS-1$
            if( object instanceof SettingsProvider ) {
                return (SettingsProvider)object;
            }
        } catch( CoreException e ) {
            e.printStackTrace();
        }
        return null;
    }
}
