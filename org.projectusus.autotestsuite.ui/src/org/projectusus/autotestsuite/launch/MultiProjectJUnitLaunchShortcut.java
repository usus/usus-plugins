package org.projectusus.autotestsuite.launch;

import static org.projectusus.autotestsuite.ui.internal.AutoTestSuitePlugin.logStatusOf;

import java.util.Collection;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.projectusus.autotestsuite.core.internal.AllJavaProjectsInWorkspace;
import org.projectusus.autotestsuite.core.internal.CommonDependencyRoot;
import org.projectusus.jfeet.selection.ElementsFrom;

public class MultiProjectJUnitLaunchShortcut implements ILaunchShortcut {

    public void launch( ISelection selection, String mode ) {
        launch( new ElementsFrom( selection ).as( IJavaProject.class ), mode );
    }

    public void launch( IEditorPart editor, String mode ) {
        // unsupported
    }

    private CommonDependencyRoot commonDependencyRoot() {
        return new CommonDependencyRoot( new AllJavaProjectsInWorkspace() );
    }

    private void launch( Collection<IJavaProject> projects, String mode ) {
        IJavaProject root = commonDependencyRoot().findFor( projects );
        try {
            ExtendedJUnitLaunchConfigurationCreator creator = new ExtendedJUnitLaunchConfigurationCreator();
            ILaunchConfiguration config = creator.createNewConfig( root, projects );
            DebugUITools.launch( config, mode );
        } catch( CoreException exception ) {
            logStatusOf( exception );
        }
    }

}
