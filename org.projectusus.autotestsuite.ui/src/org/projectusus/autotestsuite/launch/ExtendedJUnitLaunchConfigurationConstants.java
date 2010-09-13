package org.projectusus.autotestsuite.launch;

import static com.google.common.base.Strings.isNullOrEmpty;
import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;

import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.internal.junit.launcher.TestKindRegistry;

public class ExtendedJUnitLaunchConfigurationConstants {

    public static final String LAUNCH_CONFIGURATION_TYPE_ID = "org.projectusus.autotestsuite.launch.ExtendedJUnitLaunchConfiguration";

    public static final String ATTR_CHECKED_PROJECTS = "ATTR_CHECKED_PROJECTS";
    public static final char CHECKED_PROJECTS_SEPARATOR = '/';

    @SuppressWarnings( "restriction" )
    public static final String DEFAULT_TEST_KIND_ID = TestKindRegistry.JUNIT4_TEST_KIND_ID;

    public static ILaunchManager getLaunchManager() {
        return DebugPlugin.getDefault().getLaunchManager();
    }

    public static ILaunchConfigurationType getLaunchConfigurationType() {
        return getLaunchManager().getLaunchConfigurationType( LAUNCH_CONFIGURATION_TYPE_ID );
    }

    public static IJavaProject toProject( String projectName ) {
        if( isNullOrEmpty( projectName ) ) {
            return null;
        }
        return JavaCore.create( getWorkspace().getRoot() ).getJavaProject( projectName );
    }

}
