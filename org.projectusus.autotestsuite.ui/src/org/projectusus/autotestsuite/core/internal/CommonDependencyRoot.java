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
        ProjectRank result = new ProjectRank();
        for( IJavaProject project : allProjects.find() ) {
            Collection<IJavaProject> requiredProjects = requiredProjectsFinder.findFor( project );
            if( requiredProjects.containsAll( projects ) ) {
                result.update( project, requiredProjects.size() );
            }
        }
        return result.getProject();
    }

    public boolean existsFor( Collection<IJavaProject> projects ) {
        return findFor( projects ) != null;
    }

    class ProjectRank {
        private int size;
        private IJavaProject project;

        IJavaProject getProject() {
            return project;
        }

        void update( IJavaProject project, int size ) {
            if( isMoreInterestingThanPreviouslyFoundResult( size ) ) {
                this.size = size;
                this.project = project;
            }
        }

        private boolean isMoreInterestingThanPreviouslyFoundResult( int size ) {
            return project == null || size < this.size;
        }
    }
}
