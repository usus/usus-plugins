// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.project;

import org.eclipse.core.resources.IProject;
import org.osgi.service.prefs.Preferences;
import org.projectusus.core.UsusCorePlugin;
import org.projectusus.core.project2.IUSUSProject;

class UsusProject implements IUSUSProject {

    private static final String ATT_USUS_PROJECT = "ususProject"; //$NON-NLS-1$
    private static final String ENABLED = "enabled"; //$NON-NLS-1$
    private final IProject project;

    UsusProject( IProject project ) {
        this.project = project;
    }

    public void setUsusProject( boolean ususProject ) {
        getPreferences().putBoolean( ENABLED, ususProject );
    }

    public boolean isUsusProject() {
        return getPreferences().getBoolean( ENABLED, false );
    }

    public String getProjectName() {
        return project.getName();
    }

    // internal
    // /////////

    private Preferences getPreferences() {
        return UsusCorePlugin.getDefault().getPreferences().node( ATT_USUS_PROJECT ).node( getProjectName() );
    }

}
