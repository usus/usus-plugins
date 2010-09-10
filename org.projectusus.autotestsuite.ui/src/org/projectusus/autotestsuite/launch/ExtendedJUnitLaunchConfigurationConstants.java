package org.projectusus.autotestsuite.launch;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.Iterables.toArray;
import static com.google.common.collect.Iterables.transform;
import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;

import java.util.Collection;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

public class ExtendedJUnitLaunchConfigurationConstants {

    public static final String ATTR_CHECKED_PROJECTS = "ATTR_CHECKED_PROJECTS";
    private static final char CHECKED_PROJECTS_SEPARATOR = '/';

    public static ILaunchManager getLaunchManager() {
        return DebugPlugin.getDefault().getLaunchManager();
    }

    public static String getLaunchConfigurationTypeId() {
        return "org.projectusus.autotestsuite.launch.ExtendedJUnitLaunchConfiguration";
    }

    public static ILaunchConfigurationType getLaunchConfigType() {
        return getLaunchManager().getLaunchConfigurationType( getLaunchConfigurationTypeId() );
    }

    public static IJavaProject[] loadCheckedProjects( ILaunchConfiguration config ) throws CoreException {
        String value = config.getAttribute( ATTR_CHECKED_PROJECTS, "" );
        if( isNullOrEmpty( value ) ) {
            return new IJavaProject[0];
        }
        Iterable<String> split = Splitter.on( CHECKED_PROJECTS_SEPARATOR ).split( value );
        Iterable<IJavaProject> projects = transform( split, new Function<String, IJavaProject>() {
            public IJavaProject apply( String name ) {
                return toProject( name );
            }
        } );
        return toArray( projects, IJavaProject.class );
    }

    public static void saveCheckedProjects( ILaunchConfigurationWorkingCopy config, Collection<IJavaProject> checkedProjects ) {
        String value = Joiner.on( CHECKED_PROJECTS_SEPARATOR ).join( transform( checkedProjects, new Function<Object, String>() {
            public String apply( Object project ) {
                return ((IJavaProject)project).getElementName();
            }
        } ) );
        config.setAttribute( ATTR_CHECKED_PROJECTS, value );
    }

    public static IJavaProject toProject( String projectName ) {
        if( isNullOrEmpty( projectName ) ) {
            return null;
        }
        return JavaCore.create( getWorkspace().getRoot() ).getJavaProject( projectName );
    }
}
