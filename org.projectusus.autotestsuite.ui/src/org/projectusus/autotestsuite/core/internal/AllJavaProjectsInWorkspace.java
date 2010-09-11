package org.projectusus.autotestsuite.core.internal;

import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;

import org.eclipse.jdt.core.IJavaModel;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.projectusus.autotestsuite.ui.internal.AutoTestSuitePlugin;

public class AllJavaProjectsInWorkspace implements IAllJavaProjects {

    public IJavaProject[] find() {
        IJavaProject[] result = new IJavaProject[0];
        try {
            IJavaModel rootModel = JavaCore.create( getWorkspace().getRoot() );
            result = rootModel.getJavaProjects();
        } catch( JavaModelException exception ) {
            AutoTestSuitePlugin.log( exception.getStatus() );
        }
        return result;
    }
}
