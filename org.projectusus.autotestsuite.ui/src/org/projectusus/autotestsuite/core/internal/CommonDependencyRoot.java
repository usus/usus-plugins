package org.projectusus.autotestsuite.core.internal;

import java.util.Collection;

import org.eclipse.jdt.core.IJavaProject;

public class CommonDependencyRoot {

    private final RequiredJavaProjects requiredProjectsFinder;
    private final IAllJavaProjects allProjects;

    public CommonDependencyRoot( IAllJavaProjects allProjects ) {
        this.allProjects = allProjects;
        requiredProjectsFinder = new RequiredJavaProjects( allProjects );
    }

    public IJavaProject findFor( Collection<IJavaProject> projects ) {
        for( IJavaProject project : allProjects.find() ) {
            Collection<IJavaProject> requiredProjects = requiredProjectsFinder.findFor( project );
            if( requiredProjects.containsAll( projects ) ) {
                return project;
            }
        }
        return null;
    }

    public boolean existsFor( Collection<IJavaProject> projects ) {
        return findFor( projects ) != null;
    }
}
