package org.projectusus.autotestsuite.launch;

import static org.eclipse.jdt.internal.junit.launcher.JUnitLaunchConfigurationConstants.ATTR_KEEPRUNNING;
import static org.eclipse.jdt.internal.junit.launcher.JUnitLaunchConfigurationConstants.ATTR_TEST_CONTAINER;
import static org.eclipse.jdt.internal.junit.launcher.JUnitLaunchConfigurationConstants.ATTR_TEST_METHOD_NAME;
import static org.eclipse.jdt.internal.junit.launcher.JUnitLaunchConfigurationConstants.ATTR_TEST_RUNNER_KIND;
import static org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME;
import static org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME;
import static org.eclipse.jdt.ui.JavaElementLabels.ALL_FULLY_QUALIFIED;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.internal.junit.launcher.AssertionVMArg;
import org.eclipse.jdt.internal.junit.launcher.JUnitMigrationDelegate;
import org.eclipse.jdt.internal.junit.launcher.TestKindRegistry;
import org.eclipse.jdt.ui.JavaElementLabels;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.projectusus.autotestsuite.ui.internal.AutoTestSuitePlugin;

public class ExtendedJUnitLaunchShortcut implements ILaunchShortcut {

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
            config = createAndSaveNewConfig( project, config );
        }
        if( config != null ) {
            DebugUITools.launch( config, mode );
        }
    }

    private ILaunchConfiguration createAndSaveNewConfig( IJavaProject project, ILaunchConfiguration config ) {
        try {
            return createNewConfig( project ).doSave();
        } catch( CoreException e ) {
            AutoTestSuitePlugin.getDefault().getLog().log( new Status( IStatus.ERROR, null, "Could not create config", e ) );
            return null;
        }
    }

    private ILaunchConfiguration findExistingConfig( IJavaProject project ) {
        try {
            ILaunchConfiguration[] configs = getLaunchManager().getLaunchConfigurations( getLaunchConfigType() );
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

    private ILaunchManager getLaunchManager() {
        return DebugPlugin.getDefault().getLaunchManager();
    }

    protected String getLaunchConfigurationTypeId() {
        return "org.projectusus.autotestsuite.launch.ExtendedJUnitLaunchConfiguration";
    }

    protected ILaunchConfigurationWorkingCopy createNewConfig( IJavaProject project ) throws CoreException {
        String name = JavaElementLabels.getTextLabel( project, ALL_FULLY_QUALIFIED );
        ILaunchConfigurationWorkingCopy config = getLaunchConfigType().newInstance( null, getLaunchManager().generateLaunchConfigurationName( name ) );
        return fillAttributes( project, config );
    }

    private ILaunchConfigurationType getLaunchConfigType() {
        return getLaunchManager().getLaunchConfigurationType( getLaunchConfigurationTypeId() );
    }

    private ILaunchConfigurationWorkingCopy fillAttributes( IJavaProject project, ILaunchConfigurationWorkingCopy config ) throws CoreException {
        config.setAttribute( ATTR_MAIN_TYPE_NAME, "" );
        config.setAttribute( ATTR_PROJECT_NAME, project.getElementName() );
        config.setAttribute( ATTR_TEST_METHOD_NAME, "" );
        config.setAttribute( ATTR_KEEPRUNNING, false );
        config.setAttribute( ATTR_TEST_CONTAINER, project.getElementName() );
        config.setAttribute( ATTR_TEST_RUNNER_KIND, TestKindRegistry.getContainerTestKindId( project ) );
        JUnitMigrationDelegate.mapResources( config );
        AssertionVMArg.setArgDefault( config );
        return config;
    }

}
