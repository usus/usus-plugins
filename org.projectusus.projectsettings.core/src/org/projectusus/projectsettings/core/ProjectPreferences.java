package org.projectusus.projectsettings.core;

import java.util.Properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.osgi.util.NLS;
import org.osgi.service.prefs.BackingStoreException;
import org.projectusus.projectsettings.internal.util.CoreTexts;

public class ProjectPreferences extends Preferences {

    private static IEclipsePreferences getJdtPreferences( IProject project ) {
        return new ProjectScope( project ).getNode( JavaCore.PLUGIN_ID );
    }

    private static Properties getJdtPreferencesAsProperties( IProject project ) {
        Properties properties = new Properties();
        try {
            IEclipsePreferences jdtPreferences = getJdtPreferences( project );
            for( String key : jdtPreferences.keys() ) {
                properties.put( key, jdtPreferences.get( key, "" ) );
            }
        } catch( BackingStoreException bse ) {
            throw new RuntimeException( CoreTexts.Error_load_settings, bse );
        }
        return properties;
    }

    private final IProject project;

    public ProjectPreferences( IProject project ) {
        this( project, getJdtPreferencesAsProperties( project ) );
    }

    ProjectPreferences( IProject project, Properties prefsAsProps ) {
        super( NLS.bind( CoreTexts.projectSettings_settingsName, project.getName() ), prefsAsProps );
        this.project = project;
    }

    public void persist() {
        IEclipsePreferences jdtPreferences = getJdtPreferences( project );
        for( Object keyAsObject : getAll().keySet() ) {
            String key = (String)keyAsObject;
            jdtPreferences.put( key, getAll().getProperty( key ) );
        }
        try {
            jdtPreferences.flush();
        } catch( BackingStoreException bse ) {
            throw new RuntimeException( CoreTexts.Error_save_settings, bse );
        }
    }

}
