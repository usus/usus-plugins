package org.projectusus.autotestsuite.ui;

import static java.util.Collections.emptyList;

import java.util.Collection;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.projectusus.autotestsuite.core.internal.AllJavaProjectsInWorkspace;
import org.projectusus.autotestsuite.core.internal.CommonDependencyRoot;
import org.projectusus.autotestsuite.launch.ExtendedJUnitLaunchConfigurationCreator;
import org.projectusus.jfeet.selection.ElementsFrom;

public class RunTestsInSelectedProjects implements IObjectActionDelegate {

    private Collection<IJavaProject> projects = emptyList();

    public void run( IAction action ) {
        IJavaProject root = commonDependencyRoot().findFor( projects );
        try {
            ILaunchConfiguration config = new ExtendedJUnitLaunchConfigurationCreator().createNewConfig( root, projects );
            DebugUITools.launch( config, "run" );
        } catch( CoreException exception ) {
            // ignore
        }
    }

    public void selectionChanged( IAction action, ISelection selection ) {
        projects = new ElementsFrom( selection ).as( IJavaProject.class );
        action.setEnabled( commonDependencyRoot().existsFor( projects ) );
    }

    public void setActivePart( IAction action, IWorkbenchPart targetPart ) {
        // unused
    }

    private CommonDependencyRoot commonDependencyRoot() {
        return new CommonDependencyRoot( new AllJavaProjectsInWorkspace() );
    }
}
