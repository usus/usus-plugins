package org.projectusus.autotestsuite.launch;

import static org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME;
import static org.projectusus.autotestsuite.launch.ExtendedJUnitLaunchConfigurationConstants.getLaunchConfigurationType;
import static org.projectusus.autotestsuite.launch.ExtendedJUnitLaunchConfigurationConstants.getLaunchManager;
import static org.projectusus.autotestsuite.ui.internal.AutoTestSuitePlugin.logStatusOf;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;

public class ExtendedJUnitLaunchShortcut implements ILaunchShortcut {

    private final ExtendedJUnitLaunchConfigurationCreator creator = new ExtendedJUnitLaunchConfigurationCreator();

    public void launch( ISelection selection, String mode ) {
        if( selection instanceof IStructuredSelection ) {
            Object first = ((IStructuredSelection)selection).getFirstElement();
            if( first instanceof IJavaElement ) {
                launch( (IJavaElement)first, mode );
            }
        }
    }

    private void launch( IJavaElement element, String mode ) {
        IJavaProject project = element.getJavaProject();
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
        IEditorInput input = editor.getEditorInput();
        IJavaElement javaElement = (IJavaElement)input.getAdapter( IJavaElement.class );
        if( javaElement != null ) {
            launch( javaElement, mode );
        }
    }

}
