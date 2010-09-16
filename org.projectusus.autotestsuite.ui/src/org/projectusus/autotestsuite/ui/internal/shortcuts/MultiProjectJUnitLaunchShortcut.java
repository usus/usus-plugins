package org.projectusus.autotestsuite.ui.internal.shortcuts;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.ISelection;
import org.projectusus.autotestsuite.core.internal.AllJavaProjectsInWorkspace;
import org.projectusus.autotestsuite.core.internal.CommonDependencyRoot;
import org.projectusus.jfeet.selection.ElementsFrom;

public class MultiProjectJUnitLaunchShortcut extends AbstractLaunchShortcut<List<IJavaProject>> {

    @Override
    protected List<IJavaProject> extract( ISelection selection ) {
        return new ElementsFrom( selection ).as( IJavaProject.class );
    }

    @Override
    protected ILaunchConfiguration findOrCreateConfig( List<IJavaProject> projects ) throws CoreException {
        IJavaProject root = commonDependencyRoot().findFor( projects );
        return creator().createNewConfig( root, projects );
    }

    private CommonDependencyRoot commonDependencyRoot() {
        return new CommonDependencyRoot( new AllJavaProjectsInWorkspace() );
    }
}
