package org.projectusus.autotestsuite.launch;

import static com.google.common.collect.Iterables.transform;

import java.util.Collection;

import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.internal.junit.launcher.ITestKind;
import org.eclipse.jdt.internal.junit.launcher.JUnitLaunchConfigurationConstants;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;

import com.google.common.base.Function;
import com.google.common.base.Joiner;

@SuppressWarnings( "restriction" )
public class ExtendedJUnitLaunchConfigurationWriter {

    private final ILaunchConfigurationWorkingCopy config;

    public ExtendedJUnitLaunchConfigurationWriter( ILaunchConfigurationWorkingCopy config ) {
        this.config = config;
    }

    public void setCheckedProjects( Collection<IJavaProject> checkedProjects ) {
        String value = Joiner.on( ExtendedJUnitLaunchConfigurationConstants.CHECKED_PROJECTS_SEPARATOR ).join( transform( checkedProjects, new Function<Object, String>() {
            public String apply( Object project ) {
                return ((IJavaProject)project).getElementName();
            }
        } ) );
        config.setAttribute( ExtendedJUnitLaunchConfigurationConstants.ATTR_CHECKED_PROJECTS, value );
    }

    public void setTestKind( String testKindId ) {
        config.setAttribute( JUnitLaunchConfigurationConstants.ATTR_TEST_RUNNER_KIND, testKindId );
    }

    public void setProjectName( String name ) {
        config.setAttribute( IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, name );
    }

    public void setTestContainer( String identifier ) {
        config.setAttribute( JUnitLaunchConfigurationConstants.ATTR_TEST_CONTAINER, identifier );
    }

    public void setUnusedAttributesToDefaults() {
        config.setAttribute( IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, "" ); //$NON-NLS-1$
        config.setAttribute( JUnitLaunchConfigurationConstants.ATTR_TEST_METHOD_NAME, "" ); //$NON-NLS-1$
    }

    public void setKeepRunning( boolean keepRunning ) {
        config.setAttribute( JUnitLaunchConfigurationConstants.ATTR_KEEPRUNNING, keepRunning );
    }

    public void setTestKind( ITestKind testKind ) {
        config.setAttribute( JUnitLaunchConfigurationConstants.ATTR_TEST_RUNNER_KIND, testKind.getId() );
    }

    public void setDefaultTestKind() {
        setTestKind( ExtendedJUnitLaunchConfigurationConstants.DEFAULT_TEST_KIND_ID );
    }

    public void renameTo( String inputName ) {
        config.rename( inputName );
    }

}
