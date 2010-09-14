package org.projectusus.autotestsuite.launch;

import static org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME;
import static org.projectusus.autotestsuite.launch.ExtendedJUnitLaunchConfigurationConstants.getLaunchConfigurationType;
import static org.projectusus.autotestsuite.launch.ExtendedJUnitLaunchConfigurationConstants.getLaunchManager;
import static org.projectusus.autotestsuite.ui.internal.AutoTestSuitePlugin.logStatusOf;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.projectusus.jfeet.selection.ElementFrom;

public class ExtendedJUnitLaunchShortcut implements ILaunchShortcut {

    private final ExtendedJUnitLaunchConfigurationCreator creator = new ExtendedJUnitLaunchConfigurationCreator();

    public void launch( ISelection selection, String mode ) {
        launch( new ElementFrom( selection ).as( IJavaProject.class ), mode );
    }

    private void launch( IJavaProject project, String mode ) {
        ILaunchConfiguration config = findExistingConfig( project );
        if( config == null ) {
            config = creator.createAndSaveNewConfig( project );
        }
        if( config != null ) {
            DebugUITools.launch( config, mode );
        }
    }

    private ILaunchConfiguration findExistingConfig( IJavaProject project ) {
        // TODO wollen wir das? und wenn ja, wie...

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

    public void launch( IEditorPart editor, String mode ) {
        // unsupported
    }
}
