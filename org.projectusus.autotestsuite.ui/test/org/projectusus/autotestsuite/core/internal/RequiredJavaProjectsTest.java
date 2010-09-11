package org.projectusus.autotestsuite.core.internal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaModelException;
import org.junit.Test;

public class RequiredJavaProjectsTest {
    private final Map<IJavaProject, Set<String>> projects = new HashMap<IJavaProject, Set<String>>();

    @Test
    public void dependencyLessProjectFindsNothing() throws Exception {
        RequiredJavaProjects finder = new RequiredJavaProjects( projects() );
        IJavaProject project = addProject( "p1" );
        assertThat( finder.findFor( project ).size(), is( 1 ) );
        assertThat( finder.findFor( project ), hasItem( project ) );
    }

    @Test
    public void singleDependencyIsFound() throws Exception {
        RequiredJavaProjects finder = new RequiredJavaProjects( projects() );
        IJavaProject project = addProject( "p1" );
        IJavaProject dependency = addProject( "p2" );
        addDependency( project, dependency );
        assertThat( finder.findFor( project ).size(), is( 2 ) );
        assertThat( finder.findFor( project ), hasItem( project ) );
        assertThat( finder.findFor( project ), hasItem( dependency ) );
    }

    @Test
    public void multipleDependenciesAreFound() throws Exception {
        RequiredJavaProjects finder = new RequiredJavaProjects( projects() );
        IJavaProject project = addProject( "p1" );
        IJavaProject dependencyLeft = addProject( "p2" );
        addDependency( project, dependencyLeft );
        IJavaProject dependencyRight = addProject( "p3" );
        addDependency( project, dependencyRight );
        assertThat( finder.findFor( project ).size(), is( 3 ) );
        assertThat( finder.findFor( project ), hasItem( project ) );
        assertThat( finder.findFor( project ), hasItem( dependencyLeft ) );
        assertThat( finder.findFor( project ), hasItem( dependencyRight ) );
    }

    @Test
    public void indirectDependencyIsFound() throws Exception {
        RequiredJavaProjects finder = new RequiredJavaProjects( projects() );
        IJavaProject project = addProject( "p1" );
        IJavaProject directDependency = addProject( "p2" );
        addDependency( project, directDependency );
        IJavaProject indirectDependency = addProject( "p3" );
        addDependency( directDependency, indirectDependency );
        assertThat( finder.findFor( project ).size(), is( 3 ) );
        assertThat( finder.findFor( project ), hasItem( project ) );
        assertThat( finder.findFor( project ), hasItem( directDependency ) );
        assertThat( finder.findFor( project ), hasItem( indirectDependency ) );
    }

    private IAllJavaProjects projects() {
        return new IAllJavaProjects() {
            public IJavaProject[] find() {
                Set<IJavaProject> all = projects.keySet();
                return all.toArray( new IJavaProject[all.size()] );
            }
        };
    }

    private IJavaProject addProject( String name ) throws JavaModelException {
        IJavaProject project = mock( IJavaProject.class );
        when( project.getElementName() ).thenReturn( name );
        projects.put( project, new HashSet<String>() );
        wireMockDependencies( project );
        return project;
    }

    private void addDependency( IJavaProject project, IJavaProject dependency ) throws JavaModelException {
        projects.get( project ).add( dependency.getElementName() );
        wireMockDependencies( project );
    }

    private void wireMockDependencies( IJavaProject project ) throws JavaModelException {
        when( project.getRequiredProjectNames() ).thenReturn( allDependencyNamesFor( project ) );
    }

    private String[] allDependencyNamesFor( IJavaProject project ) {
        Set<String> dependencies = projects.get( project );
        return dependencies.toArray( new String[dependencies.size()] );
    }
}
