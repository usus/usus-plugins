package org.projectusus.autotestsuite.core.internal.config;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.Iterables.toArray;
import static com.google.common.collect.Iterables.transform;
import static org.eclipse.jdt.internal.junit.launcher.JUnitLaunchConfigurationConstants.ATTR_KEEPRUNNING;
import static org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME;
import static org.projectusus.autotestsuite.AutoTestSuitePlugin.logStatusOf;
import static org.projectusus.autotestsuite.core.internal.config.ExtendedJUnitLaunchConfigurationConstants.ATTR_CHECKED_PROJECTS;
import static org.projectusus.autotestsuite.core.internal.config.ExtendedJUnitLaunchConfigurationConstants.CHECKED_PROJECTS_SEPARATOR;
import static org.projectusus.autotestsuite.core.internal.config.ExtendedJUnitLaunchConfigurationConstants.DEFAULT_TEST_KIND_ID;
import static org.projectusus.autotestsuite.core.internal.config.ExtendedJUnitLaunchConfigurationConstants.toProject;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.internal.junit.launcher.ITestKind;
import org.eclipse.jdt.internal.junit.launcher.JUnitLaunchConfigurationConstants;
import org.eclipse.jdt.internal.junit.launcher.TestKindRegistry;

import com.google.common.base.Function;
import com.google.common.base.Splitter;

@SuppressWarnings( "restriction" )
public class ExtendedJUnitLaunchConfigurationReader {

    private final ILaunchConfiguration config;

    public ExtendedJUnitLaunchConfigurationReader( ILaunchConfiguration config ) {
        this.config = config;
    }

    public boolean isKeepRunning() {
        try {
            return config.getAttribute( ATTR_KEEPRUNNING, false );
        } catch( CoreException exception ) {
            logStatusOf( exception );
        }
        return false;
    }

    public ITestKind getTestKind() {
        ITestKind testKind = JUnitLaunchConfigurationConstants.getTestRunnerKind( config );
        if( testKind.isNull() ) {
            testKind = TestKindRegistry.getDefault().getKind( DEFAULT_TEST_KIND_ID );
        }
        return testKind;
    }

    public String getProjectName() {
        try {
            return config.getAttribute( ATTR_PROJECT_NAME, "" ); //$NON-NLS-1$
        } catch( CoreException exception ) {
            logStatusOf( exception );
        }
        return "";
    }

    private IJavaProject[] loadCheckedProjects( Function<String, IJavaProject> converter ) throws CoreException {
        String value = config.getAttribute( ATTR_CHECKED_PROJECTS, "" );
        if( isNullOrEmpty( value ) ) {
            return new IJavaProject[0];
        }
        Iterable<String> split = Splitter.on( CHECKED_PROJECTS_SEPARATOR ).split( value );
        Iterable<IJavaProject> projects = transform( split, converter );
        return toArray( projects, IJavaProject.class );
    }

    public IJavaProject[] getCheckedProjects() {
        return getCheckedProjects( new Function<String, IJavaProject>() {
            public IJavaProject apply( String name ) {
                return toProject( name );
            }
        } );
    }

    public IJavaProject[] getCheckedProjects( Function<String, IJavaProject> converter ) {
        try {
            return loadCheckedProjects( converter );
        } catch( CoreException exception ) {
            logStatusOf( exception );
        }
        return new IJavaProject[0];
    }

}
