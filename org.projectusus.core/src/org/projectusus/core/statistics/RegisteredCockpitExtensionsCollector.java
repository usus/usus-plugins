package org.projectusus.core.statistics;

import static com.google.common.base.Predicates.equalTo;
import static com.google.common.collect.Collections2.transform;
import static com.google.common.collect.Iterables.getOnlyElement;
import static com.google.common.collect.Sets.filter;
import static com.google.common.collect.Sets.newHashSet;
import static org.projectusus.core.statistics.CockpitExtensionPref.ON_BY_DEFAULT;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;
import org.projectusus.core.ExtensionCollector;
import org.projectusus.core.UsusCorePlugin;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

public class RegisteredCockpitExtensionsCollector {

    private static final String STATISTICS_EXTENSIONS = "statistics-extensions"; //$NON-NLS-1$
    private static final String ENABLED = "enabled"; //$NON-NLS-1$

    private static final Function<CockpitExtension, CockpitExtensionPref> toExtensionPref = new Function<CockpitExtension, CockpitExtensionPref>() {
        public CockpitExtensionPref apply( CockpitExtension extension ) {
            return new CockpitExtensionPref( extension.getClass().getName(), extension.getLabel() );
        }
    };

    private RegisteredCockpitExtensionsCollector() {
        super();
    }

    public static Set<CockpitExtension> getEnabled() {
        final SortedSet<CockpitExtensionPref> allStates = getExtensionsStates();
        return filter( allExtensions(), new Predicate<CockpitExtension>() {
            public boolean apply( CockpitExtension extension ) {
                return isEnabled( extension, allStates );
            }
        } );
    }

    private static boolean isEnabled( CockpitExtension extension, Set<CockpitExtensionPref> allStates ) {
        CockpitExtensionPref extensionPref = findByClassName( allStates, extension.getClass().getName() );
        return extensionPref == null ? ON_BY_DEFAULT : extensionPref.isOn();
    }

    public static Set<CockpitExtension> allExtensions() {
        return new ExtensionCollector<CockpitExtension>( CockpitExtension.EXTENSION_POINT_ID ).allExtensions();
    }

    private static Set<CockpitExtensionPref> getAllPresentExtensions() {
        return newHashSet( transform( allExtensions(), toExtensionPref ) );
    }

    public static SortedSet<CockpitExtensionPref> getExtensionsStates() {
        Set<CockpitExtensionPref> extensions = getAllPresentExtensions();
        Set<CockpitExtensionPref> known = getAllKnownExtensions();
        for( CockpitExtensionPref extension : extensions ) {
            CockpitExtensionPref pref = findByClassName( known, extension.getClassName() );
            if( pref != null ) {
                extension.setOn( pref.isOn() );
            }
        }
        return sort( extensions );
    }

    private static CockpitExtensionPref findByClassName( Set<CockpitExtensionPref> extensions, String className ) {
        Set<CockpitExtensionPref> filtered = filter( extensions, equalTo( new CockpitExtensionPref( className, ON_BY_DEFAULT ) ) );
        return getOnlyElement( filtered, null );
    }

    private static SortedSet<CockpitExtensionPref> sort( Set<CockpitExtensionPref> extensions ) {
        TreeSet<CockpitExtensionPref> sorted = new TreeSet<CockpitExtensionPref>( new CockpitExtensionPrefComparator() );
        sorted.addAll( extensions );
        return sorted;
    }

    private static Set<CockpitExtensionPref> getAllKnownExtensions() {
        Set<CockpitExtensionPref> known = newHashSet();
        Preferences preferences = loadPreferences();
        for( String className : childrenNames( preferences ) ) {
            Preferences node = preferences.node( className );
            boolean on = node.getBoolean( ENABLED, ON_BY_DEFAULT );
            known.add( new CockpitExtensionPref( className, on ) );
        }
        return known;
    }

    private static Preferences loadPreferences() {
        return UsusCorePlugin.getDefault().getPreferences().node( STATISTICS_EXTENSIONS );
    }

    public static void saveExtensionsStates( Set<CockpitExtensionPref> states ) {
        Preferences preferences = loadPreferences();
        for( String string : childrenNames( preferences ) ) {
            preferences.remove( string );
        }
        for( CockpitExtensionPref extensionPref : states ) {
            Preferences node = preferences.node( extensionPref.getClassName() );
            node.putBoolean( ENABLED, extensionPref.isOn() );
        }
    }

    private static String[] childrenNames( Preferences extensions ) {
        try {
            return extensions.childrenNames();
        } catch( BackingStoreException exception ) {
            log( exception );
            return new String[0];
        }
    }

    private static void log( Throwable throwable ) {
        UsusCorePlugin.log( throwable );
    }
}
