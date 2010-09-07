package org.projectusus.autotestsuite.ui;

import static java.util.Collections.emptySet;
import static org.projectusus.autotestsuite.launch.ExtendedJUnitLaunchConfigurationDelegate.findRequiredProjects;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.projectusus.autotestsuite.launch.ExtendedJUnitLaunchConfigurationCreator;

public class RunTestsInSelectedProjects implements IObjectActionDelegate {

    private Set<IJavaProject> projects = new HashSet<IJavaProject>();

    public void run( IAction action ) {
        IJavaProject root = findCommonRoot();
        try {
            ILaunchConfiguration config = new ExtendedJUnitLaunchConfigurationCreator().createNewConfig( root, projects );
            DebugUITools.launch( config, "run" );
        } catch( CoreException exception ) {
            // ignore
        }
    }

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
        action.setEnabled( haveCommonRoot() );
    }

    private boolean haveCommonRoot() {
        return findCommonRoot() != null;
    }

    private IJavaProject findCommonRoot() {
        for( IJavaProject project : getJavaProjects() ) {
            Set<IJavaProject> requiredProjects;
            try {
                requiredProjects = findRequiredProjects( project );
                requiredProjects.add( project );
            } catch( JavaModelException exception ) {
                requiredProjects = emptySet();
            }
            if( requiredProjects.containsAll( projects ) ) {
                return project;
            }
        }
        return null;
    }

    private IJavaProject[] getJavaProjects() {
        try {
            return JavaCore.create( ResourcesPlugin.getWorkspace().getRoot() ).getJavaProjects();
        } catch( JavaModelException exception ) {
            return new IJavaProject[0];
        }
    }

    public void setActivePart( IAction action, IWorkbenchPart targetPart ) {
        // unused
    }

}
