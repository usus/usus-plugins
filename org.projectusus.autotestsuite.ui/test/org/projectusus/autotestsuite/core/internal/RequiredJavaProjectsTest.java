package org.projectusus.autotestsuite.core.internal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;

import org.eclipse.jdt.core.IJavaProject;
import org.junit.Test;

public class RequiredJavaProjectsTest extends TestsWithProjectDependencies {

    private final RequiredJavaProjects finder = new RequiredJavaProjects( projects() );

    @Test
    public void dependencyLessProjectFindsNothing() throws Exception {
        IJavaProject project = addProject( "p1" );
        assertThat( finder.findFor( project ).size(), is( 1 ) );
        assertThat( finder.findFor( project ), hasItem( project ) );
    }

    @Test
    public void singleDependencyIsFound() throws Exception {
        IJavaProject project = addProject( "p1" );
        IJavaProject dependency = addProject( "p2" );
        addDependency( project, dependency );
        assertThat( finder.findFor( project ).size(), is( 2 ) );
        assertThat( finder.findFor( project ), hasItem( project ) );
        assertThat( finder.findFor( project ), hasItem( dependency ) );
    }

    @Test
    public void multipleDependenciesAreFound() throws Exception {
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
}
