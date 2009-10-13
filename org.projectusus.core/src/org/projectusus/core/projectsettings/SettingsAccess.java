// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.projectsettings;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.osgi.service.prefs.BackingStoreException;

public class SettingsAccess {

    private static final String JDT_CORE_NODE = "org.eclipse.jdt.core"; //$NON-NLS-1$

    public void save( List<IProject> projects, ProjectSettings settings ) {
        for( IProject project : projects ) {
            save( project, settings );
        }
    }

    private void save( IProject project, ProjectSettings settings ) {
        IEclipsePreferences jdtPrefercences = getJdtPreferences( project );

        List<CompilerWarningSetting> compilerWarningSettings = settings.getCompilerwarningSettings().getSettings();
        for( CompilerWarningSetting setting : compilerWarningSettings ) {
            jdtPrefercences.put( setting.getCode().getSetting(), setting.getValue().name() );
        }
        try {
            jdtPrefercences.flush();
        } catch( BackingStoreException exception ) {
            exception.printStackTrace();
        }
    }

    private IEclipsePreferences getJdtPreferences( IProject project ) {
        ProjectScope projectScope = new ProjectScope( project );
        return projectScope.getNode( JDT_CORE_NODE );
    }
}
