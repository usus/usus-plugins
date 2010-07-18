package org.projectusus.core.statistics;

import static org.eclipse.core.runtime.Platform.getExtensionRegistry;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;
import org.projectusus.core.UsusCorePlugin;
import org.projectusus.core.internal.proportions.rawdata.UsusModel;

public class RegisteredCockpitExtensionsCollector {

    private static final String STATISTICS_EXTENSIONS = "statistics-extensions"; //$NON-NLS-1$
    private static final String CLASS = "class"; //$NON-NLS-1$
    private static final String ENABLED = "enabled"; //$NON-NLS-1$
    private final static String STATISTICS_ID = "org.projectusus.core.statistics"; //$NON-NLS-1$

    private RegisteredCockpitExtensionsCollector() {
        super();
    }

    public static Set<ICockpitExtension> getEnabled() {
        Set<ICockpitExtension> result = new HashSet<ICockpitExtension>();
        Set<ICockpitExtension> allExtensions = getAll();
        for( ICockpitExtension extension : allExtensions ) {
            if( isEnabled( extension ) ) {
                result.add( extension );
            }
        }
        return result;
    }

    private static boolean isEnabled( ICockpitExtension extension ) {
        String currentName = extension.getClass().getName();
        for( CockpitExtensionPref extensionPref : getExtensionsStates() ) {
            if( currentName.equals( extensionPref.getName() ) ) {
                if( extensionPref.isOn() ) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public static Set<ICockpitExtension> getAll() {
        Set<ICockpitExtension> cockpitExtensions = new HashSet<ICockpitExtension>();
        try {
            for( IConfigurationElement statisticElement : getExtensionRegistry().getConfigurationElementsFor( STATISTICS_ID ) ) {
                final Object extension = statisticElement.createExecutableExtension( CLASS );
                if( extension instanceof ICockpitExtension ) {
                    cockpitExtensions.add( (ICockpitExtension)extension );
                }
            }
        } catch( CoreException ex ) {
            UsusCorePlugin.log( ex );
        }
        return cockpitExtensions;
    }

    private static Set<String> getAllNames() {
        Set<String> names = new HashSet<String>();
        try {
            for( IConfigurationElement statisticElement : getExtensionRegistry().getConfigurationElementsFor( STATISTICS_ID ) ) {
                final Object extension = statisticElement.createExecutableExtension( CLASS );
                if( extension instanceof ICockpitExtension ) {
                    names.add( statisticElement.getAttribute( CLASS ) );
                }
            }
        } catch( CoreException ex ) {
            UsusCorePlugin.log( ex );
        }
        return names;
    }

    public static SortedSet<CockpitExtensionPref> getExtensionsStates() {
        SortedSet<CockpitExtensionPref> prefs = new TreeSet<CockpitExtensionPref>();
        try {
            Preferences extensions = UsusCorePlugin.getDefault().getPreferences().node( STATISTICS_EXTENSIONS );
            Set<String> allNames = getAllNames();
            for( String string : extensions.childrenNames() ) {
                if( !allNames.contains( string ) ) {
                    allNames.add( string );
                }
            }
            for( String string : allNames ) {
                boolean value = extensions.node( string ).getBoolean( ENABLED, true );
                prefs.add( new CockpitExtensionPref( string, value ) );
            }
        } catch( BackingStoreException e ) {
            UsusCorePlugin.log( e );
        }
        return prefs;
    }

    public static void updateExtensionsStates( Set<CockpitExtensionPref> states ) {
        try {
            Preferences extensions = UsusCorePlugin.getDefault().getPreferences().node( STATISTICS_EXTENSIONS );
            for( String string : extensions.childrenNames() ) {
                extensions.remove( string );
            }
            for( CockpitExtensionPref extensionPref : states ) {
                extensions.node( extensionPref.getName() ).putBoolean( ENABLED, extensionPref.isOn() );
            }
            UsusCorePlugin.getDefault().savePreferences();
        } catch( BackingStoreException e ) {
            UsusCorePlugin.log( e );
        }
        UsusModel.ususModel().updateAfterComputationRun( true, new NullProgressMonitor() );
    }
}
