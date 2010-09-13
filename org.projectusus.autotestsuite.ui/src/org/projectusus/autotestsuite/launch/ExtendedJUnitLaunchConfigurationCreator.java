package org.projectusus.autotestsuite.launch;

import static java.util.Arrays.asList;
import static org.eclipse.jdt.ui.JavaElementLabels.ALL_FULLY_QUALIFIED;
import static org.projectusus.autotestsuite.launch.ExtendedJUnitLaunchConfigurationConstants.getLaunchConfigurationType;
import static org.projectusus.autotestsuite.ui.internal.AutoTestSuitePlugin.log;

import java.util.Collection;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.internal.junit.launcher.AssertionVMArg;
import org.eclipse.jdt.internal.junit.launcher.JUnitMigrationDelegate;
import org.eclipse.jdt.ui.JavaElementLabels;

public class ExtendedJUnitLaunchConfigurationCreator {

    public ILaunchConfiguration createAndSaveNewConfig( IJavaProject project ) {
        try {
            return createNewConfig( project ).doSave();
        } catch( CoreException exception ) {
            log( exception, "Could not create config" );
            return null;
        }
    }

    public ILaunchConfigurationWorkingCopy createNewConfig( IJavaProject root ) throws CoreException {
        return createNewConfig( root, asList( root.getJavaModel().getJavaProjects() ) );
    }

    public ILaunchConfigurationWorkingCopy createNewConfig( IJavaProject root, Collection<IJavaProject> projects ) throws CoreException {
        String name = JavaElementLabels.getTextLabel( root, ALL_FULLY_QUALIFIED );
        // TODO lf Eclipse 3.6
        // ILaunchConfigurationWorkingCopy config = getLaunchConfigType().newInstance( null,
        // ExtendedJUnitLaunchConfigurationConstants.getLaunchManager().generateLaunchConfigurationName( name ) );
        // TODO lf Eclipse 3.5
        ILaunchConfigurationWorkingCopy config = getLaunchConfigurationType().newInstance( null,
                ExtendedJUnitLaunchConfigurationConstants.getLaunchManager().generateUniqueLaunchConfigurationNameFrom( name ) );
        return fillAttributes( root, config, projects );
    }

    private ILaunchConfigurationWorkingCopy fillAttributes( IJavaProject project, ILaunchConfigurationWorkingCopy config, Collection<IJavaProject> projects ) throws CoreException {
        ExtendedJUnitLaunchConfigurationWriter writer = new ExtendedJUnitLaunchConfigurationWriter( config );
        writer.setUnusedAttributesToDefaults();
        writer.setProjectName( project.getElementName() );
        writer.setDefaultTestKind( );
        writer.setCheckedProjects( projects );
        writer.setKeepRunning( false );
        writer.setTestContainer( project.getElementName() );
        JUnitMigrationDelegate.mapResources( config );
        AssertionVMArg.setArgDefault( config );
        return config;
    }

}
