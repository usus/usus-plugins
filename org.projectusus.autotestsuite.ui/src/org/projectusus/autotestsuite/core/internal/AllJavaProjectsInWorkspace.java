package org.projectusus.autotestsuite.core.internal;

import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;
import static org.projectusus.autotestsuite.AutoTestSuitePlugin.logStatusOf;

import org.eclipse.jdt.core.IJavaModel;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

public class AllJavaProjectsInWorkspace implements IAllJavaProjects {

    public IJavaProject[] find() {
        IJavaProject[] result = new IJavaProject[0];
        try {
            IJavaModel rootModel = JavaCore.create( getWorkspace().getRoot() );
            result = rootModel.getJavaProjects();
        } catch( JavaModelException exception ) {
            logStatusOf( exception );
        }
        return result;
    }
}
