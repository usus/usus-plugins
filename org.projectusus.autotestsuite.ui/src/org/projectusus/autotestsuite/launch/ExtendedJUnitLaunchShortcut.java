package org.projectusus.autotestsuite.launch;

import static org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME;

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
            config = creator.createAndSaveNewConfig( project, config );
        }
        if( config != null ) {
            DebugUITools.launch( config, mode );
        }
    }

    private ILaunchConfiguration findExistingConfig( IJavaProject project ) {
        try {
            ILaunchConfiguration[] configs = ExtendedJUnitLaunchConfigurationConstants.getLaunchManager().getLaunchConfigurations(
                    ExtendedJUnitLaunchConfigurationConstants.getLaunchConfigType() );
            String projectName = project.getElementName();
            for( ILaunchConfiguration currentConfig : configs ) {
                if( currentConfig.getAttribute( ATTR_PROJECT_NAME, "" ).equals( projectName ) ) {
                    return currentConfig;
                }
            }
        } catch( CoreException exception ) {
            // ignore
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
