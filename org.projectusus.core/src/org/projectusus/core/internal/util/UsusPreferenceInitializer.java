// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.util;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.osgi.service.prefs.BackingStoreException;
import org.projectusus.core.internal.UsusCorePlugin;

public class UsusPreferenceInitializer extends AbstractPreferenceInitializer {

    @Override
    public void initializeDefaultPreferences() {
        try {
            initPreferences();
        } catch( BackingStoreException bastox ) {
            UsusCorePlugin.log( bastox );
        }
    }

    private void initPreferences() throws BackingStoreException {
        IEclipsePreferences prefs = getPreferencesDefaults();
        setDefaultValues( prefs );
        prefs.flush();
    }

    private IEclipsePreferences getPreferencesDefaults() {
        return new DefaultScope().getNode( UsusCorePlugin.PLUGIN_ID );
    }

    private void setDefaultValues( IEclipsePreferences prefs ) {
        prefs.putBoolean( UsusPreferenceKeys.AUTO_COMPUTE, true );
    }
}
