// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.projectsettings;

import static org.projectusus.core.internal.util.CoreTexts.projectSettings_settingsName;

import java.util.List;
import java.util.Properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.osgi.util.NLS;
import org.osgi.service.prefs.BackingStoreException;

public class SettingsAccess {

    private static final String JDT_CORE_NODE = "org.eclipse.jdt.core"; //$NON-NLS-1$

    public void save( List<IProject> projects, ProjectSettings settings ) {
        for( IProject project : projects ) {
            save( project, settings );
        }
    }

    private void save( IProject project, ProjectSettings settings ) {
        List<CompilerWarningSetting> compilerWarningSettings = settings.getCompilerwarningSettings().getSettings();
        try {
            IEclipsePreferences jdtPrefercences = getJdtPreferences( project );
            for( CompilerWarningSetting setting : compilerWarningSettings ) {
                jdtPrefercences.put( setting.getCode().getSetting(), setting.getValue().name() );
            }
            jdtPrefercences.flush();
        } catch( BackingStoreException exception ) {
            exception.printStackTrace();
            throw new RuntimeException( "Could not save settings.", exception );
        }
    }

    private IEclipsePreferences getJdtPreferences( IProject project ) {
        ProjectScope projectScope = new ProjectScope( project );
        return projectScope.getNode( JDT_CORE_NODE );
    }

    public ProjectSettings loadSettings( IProject project ) {
        String title = NLS.bind( projectSettings_settingsName, project.getName() );
        ProjectSettings settings = new ProjectSettings( title );
        try {
            IEclipsePreferences jdtPrefercences = getJdtPreferences( project );
            Properties properties = convertToProperties( jdtPrefercences );
            settings.getCompilerwarningSettings().loadValuesFromProperties( properties );
        } catch( BackingStoreException exception ) {
            exception.printStackTrace();
            throw new RuntimeException( "Could not load Settings", exception );
        }
        return settings;
    }

    private Properties convertToProperties( IEclipsePreferences jdtPrefercences ) throws BackingStoreException {
        Properties result = new Properties();
        String[] keys = jdtPrefercences.keys();
        for( String key : keys ) {
            result.put( key, jdtPrefercences.get( key, null ) );
        }
        return result;
    }

}
