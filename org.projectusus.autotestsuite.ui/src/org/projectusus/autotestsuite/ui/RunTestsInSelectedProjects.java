package org.projectusus.autotestsuite.ui;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.projectusus.autotestsuite.core.internal.AllJavaProjectsInWorkspace;
import org.projectusus.autotestsuite.core.internal.CommonDependencyRoot;
import org.projectusus.autotestsuite.launch.ExtendedJUnitLaunchConfigurationCreator;

public class RunTestsInSelectedProjects implements IObjectActionDelegate {

    private Set<IJavaProject> projects = new HashSet<IJavaProject>();

    public void run( IAction action ) {
        IJavaProject root = commonDependencyRoot().findFor( projects );
        try {
            ILaunchConfiguration config = new ExtendedJUnitLaunchConfigurationCreator().createNewConfig( root, projects );
            DebugUITools.launch( config, "run" );
        } catch( CoreException exception ) {
            // ignore
        }
    }

    // TODO lf use ElementFrom
    public void selectionChanged( IAction action, ISelection selection ) {
        projects = new HashSet<IJavaProject>();
        if( selection instanceof IStructuredSelection ) {
            IStructuredSelection structured = (IStructuredSelection)selection;
            for( Object object : structured.toList() ) {
                if( object instanceof IAdaptable ) {
                    IAdaptable adaptable = (IAdaptable)object;
                    Object adapter = adaptable.getAdapter( IJavaProject.class );
                    if( adapter instanceof IJavaProject ) {
                        projects.add( (IJavaProject)adapter );
                    }
                }
            }
        }
        action.setEnabled( commonDependencyRoot().existsFor( projects ) );
    }

    public void setActivePart( IAction action, IWorkbenchPart targetPart ) {
        // unused
    }

    private CommonDependencyRoot commonDependencyRoot() {
        return new CommonDependencyRoot( new AllJavaProjectsInWorkspace() );
    }
}
