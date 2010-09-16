package org.projectusus.autotestsuite.ui.internal.shortcuts;

import static org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME;
import static org.projectusus.autotestsuite.AutoTestSuitePlugin.logStatusOf;
import static org.projectusus.autotestsuite.core.internal.config.ExtendedJUnitLaunchConfigurationConstants.getLaunchConfigurationType;
import static org.projectusus.autotestsuite.core.internal.config.ExtendedJUnitLaunchConfigurationConstants.getLaunchManager;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.ISelection;
import org.projectusus.jfeet.selection.ElementFrom;

public class ExtendedJUnitLaunchShortcut extends AbstractLaunchShortcut<IJavaProject> {

    @Override
    protected IJavaProject extract( ISelection selection ) {
        return new ElementFrom( selection ).as( IJavaProject.class );
    }

    @Override
    protected ILaunchConfiguration findOrCreateConfig( IJavaProject project ) throws CoreException {
        ILaunchConfiguration config = findExistingConfig( project );
        if( config == null ) {
            config = creator().createAndSaveNewConfig( project );
        }
        return config;
    }

    private ILaunchConfiguration findExistingConfig( IJavaProject project ) {
        try {
            ILaunchConfiguration[] configs = getLaunchManager().getLaunchConfigurations( getLaunchConfigurationType() );
            String projectName = project.getElementName();
            for( ILaunchConfiguration currentConfig : configs ) {
                if( currentConfig.getAttribute( ATTR_PROJECT_NAME, "" ).equals( projectName ) ) {
                    return currentConfig;
                }
            }
        } catch( CoreException exception ) {
            logStatusOf( exception );
        }
        return null;
    }
}
