package org.projectusus.autotestsuite.launch;

import static com.google.common.collect.Iterables.toArray;
import static com.google.common.collect.Iterables.transform;

import java.util.Collection;

import org.eclipse.core.resources.ResourcesPlugin;
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
        IJavaProject[] checkedProjects;
        String value = config.getAttribute( ATTR_CHECKED_PROJECTS, "" );
        if( "".equals( value ) ) {
            checkedProjects = new IJavaProject[0];
        } else {
            Iterable<String> split = Splitter.on( CHECKED_PROJECTS_SEPARATOR ).split( value );
            Iterable<IJavaProject> projects = transform( split, new Function<String, IJavaProject>() {
                public IJavaProject apply( String name ) {
                    return toProject( name );
                }
            } );
            checkedProjects = toArray( projects, IJavaProject.class );
        }
        return checkedProjects;
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
        if( projectName == null || "".equals( projectName ) ) {
            return null;
        }
        return JavaCore.create( ResourcesPlugin.getWorkspace().getRoot().getProject( projectName ) );
    }

}
