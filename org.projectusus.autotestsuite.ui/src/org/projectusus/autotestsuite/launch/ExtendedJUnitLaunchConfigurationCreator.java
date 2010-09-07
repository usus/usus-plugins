package org.projectusus.autotestsuite.launch;

import static java.util.Arrays.asList;
import static org.eclipse.jdt.internal.junit.launcher.JUnitLaunchConfigurationConstants.ATTR_KEEPRUNNING;
import static org.eclipse.jdt.internal.junit.launcher.JUnitLaunchConfigurationConstants.ATTR_TEST_CONTAINER;
import static org.eclipse.jdt.internal.junit.launcher.JUnitLaunchConfigurationConstants.ATTR_TEST_METHOD_NAME;
import static org.eclipse.jdt.internal.junit.launcher.JUnitLaunchConfigurationConstants.ATTR_TEST_RUNNER_KIND;
import static org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME;
import static org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME;
import static org.eclipse.jdt.ui.JavaElementLabels.ALL_FULLY_QUALIFIED;
import static org.projectusus.autotestsuite.launch.ExtendedJUnitLaunchConfigurationConstants.getLaunchConfigType;
import static org.projectusus.autotestsuite.launch.ExtendedJUnitLaunchConfigurationConstants.saveCheckedProjects;

import java.util.Collection;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.internal.junit.launcher.AssertionVMArg;
import org.eclipse.jdt.internal.junit.launcher.JUnitMigrationDelegate;
import org.eclipse.jdt.internal.junit.launcher.TestKindRegistry;
import org.eclipse.jdt.ui.JavaElementLabels;
import org.projectusus.autotestsuite.ui.internal.AutoTestSuitePlugin;

public class ExtendedJUnitLaunchConfigurationCreator {

    public ILaunchConfiguration createAndSaveNewConfig( IJavaProject project, ILaunchConfiguration config ) {
        try {
            return createNewConfig( project ).doSave();
        } catch( CoreException e ) {
            AutoTestSuitePlugin.getDefault().getLog().log( new Status( IStatus.ERROR, null, "Could not create config", e ) );
            return null;
        }
    }

    public ILaunchConfigurationWorkingCopy createNewConfig( IJavaProject root ) throws CoreException {
        return createNewConfig( root, asList( root.getJavaModel().getJavaProjects() ) );
    }

    public ILaunchConfigurationWorkingCopy createNewConfig( IJavaProject root, Collection<IJavaProject> projects ) throws CoreException {
        String name = JavaElementLabels.getTextLabel( root, ALL_FULLY_QUALIFIED );
        ILaunchConfigurationWorkingCopy config = getLaunchConfigType().newInstance( null,
                ExtendedJUnitLaunchConfigurationConstants.getLaunchManager().generateLaunchConfigurationName( name ) );
        return fillAttributes( root, config, projects );
    }

    private ILaunchConfigurationWorkingCopy fillAttributes( IJavaProject project, ILaunchConfigurationWorkingCopy config, Collection<IJavaProject> projects ) throws CoreException {
        config.setAttribute( ATTR_MAIN_TYPE_NAME, "" );
        config.setAttribute( ATTR_PROJECT_NAME, project.getElementName() );
        config.setAttribute( ATTR_TEST_METHOD_NAME, "" );
        config.setAttribute( ATTR_TEST_RUNNER_KIND, TestKindRegistry.JUNIT4_TEST_KIND_ID );
        saveCheckedProjects( config, projects );
        config.setAttribute( ATTR_KEEPRUNNING, false );
        config.setAttribute( ATTR_TEST_CONTAINER, project.getElementName() );
        JUnitMigrationDelegate.mapResources( config );
        AssertionVMArg.setArgDefault( config );
        return config;
    }

}
