package org.projectusus.autotestsuite.core.internal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaModelException;

abstract class TestsWithProjectDependencies {

    private final Map<IJavaProject, Set<String>> projects = new HashMap<IJavaProject, Set<String>>();

    IAllJavaProjects projects() {
        return new IAllJavaProjects() {
            public IJavaProject[] find() {
                Set<IJavaProject> all = projects.keySet();
                return all.toArray( new IJavaProject[all.size()] );
            }
        };
    }

    IJavaProject addProject( String name ) throws JavaModelException {
        IJavaProject project = mock( IJavaProject.class );
        when( project.getElementName() ).thenReturn( name );
        when( project.toString() ).thenReturn( "Mock project: " + name );
        projects.put( project, new HashSet<String>() );
        wireMockDependencies( project );
        return project;
    }

    void addDependency( IJavaProject project, IJavaProject dependency ) throws JavaModelException {
        projects.get( project ).add( dependency.getElementName() );
        wireMockDependencies( project );
    }

    void wireMockDependencies( IJavaProject project ) throws JavaModelException {
        when( project.getRequiredProjectNames() ).thenReturn( allDependencyNamesFor( project ) );
    }

    String[] allDependencyNamesFor( IJavaProject project ) {
        Set<String> dependencies = projects.get( project );
        return dependencies.toArray( new String[dependencies.size()] );
    }

}
