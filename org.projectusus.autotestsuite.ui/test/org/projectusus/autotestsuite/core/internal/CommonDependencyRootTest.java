package org.projectusus.autotestsuite.core.internal;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.either;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.HashSet;

import org.eclipse.jdt.core.IJavaProject;
import org.junit.Test;

public class CommonDependencyRootTest extends TestsWithProjectDependencies {

    private final CommonDependencyRoot finder = new CommonDependencyRoot( projects() );

    @Test
    public void noneFoundForEmpty() {
        // [], ?[] => []
        assertThat( finder.findFor( new HashSet<IJavaProject>() ), is( nullValue() ) );
    }

    @Test
    public void singleProjectFindsItself() throws Exception {
        // [p], ?p => [p]
        IJavaProject project = addProject( "p1" );
        assertThat( finder.findFor( asList( project ) ), is( project ) );
    }

    @Test
    public void dependentProjectFindsItself() throws Exception {
        // [p1->p2], ?p1 => [p1]
        IJavaProject project = addProject( "p1" );
        IJavaProject dependency = addProject( "p2" );
        addDependency( project, dependency );
        assertThat( finder.findFor( asList( project ) ), is( project ) );
    }

    @Test
    public void dependencyFindsItself() throws Exception {
        // [p1->p2], ?p2 => [p2]
        IJavaProject project = addProject( "p1" );
        IJavaProject dependency = addProject( "p2" );
        addDependency( project, dependency );
        assertThat( finder.findFor( asList( dependency ) ), is( dependency ) );
    }

    @Test
    public void dependencyFindsDependent() throws Exception {
        // [p1->p2], ?p1,p2 => [p1]
        IJavaProject project = addProject( "p1" );
        IJavaProject dependency = addProject( "p2" );
        addDependency( project, dependency );
        assertThat( finder.findFor( asList( project, dependency ) ), is( project ) );
    }

    @Test
    public void directRootIsFound() throws Exception {
        // [p1->p2, p1->p3], ?p2,p3 => [p1]
        IJavaProject project = addProject( "p1" );
        IJavaProject dependencyLeft = addProject( "p2" );
        addDependency( project, dependencyLeft );
        IJavaProject dependencyRight = addProject( "p3" );
        addDependency( project, dependencyRight );
        assertThat( finder.findFor( asList( dependencyLeft, dependencyRight ) ), is( project ) );
    }

    @Test
    public void indirectRootIsFound() throws Exception {
        // [p1->p2, p1->p3, p3->p4], ?p2,p4 => [p1]
        IJavaProject project = addProject( "p1" );
        IJavaProject dependencyLeft = addProject( "p2" );
        addDependency( project, dependencyLeft );
        IJavaProject dependencyRight = addProject( "p3" );
        addDependency( project, dependencyRight );
        IJavaProject dependencyBottom = addProject( "p4" );
        addDependency( dependencyRight, dependencyBottom );
        assertThat( finder.findFor( asList( dependencyLeft, dependencyBottom ) ), is( project ) );
    }

    @Test
    public void intermediateRootIsFound() throws Exception {
        // [p0->p1, p1->p2, p1->p3, p3->p4], ?p2,p4 => [p1]
        IJavaProject uberRoot = addProject( "p0" );
        IJavaProject project = addProject( "p1" );
        addDependency( uberRoot, project );

        IJavaProject dependencyLeft = addProject( "p2" );
        addDependency( project, dependencyLeft );
        IJavaProject dependencyRight = addProject( "p3" );
        addDependency( project, dependencyRight );
        IJavaProject dependencyBottom = addProject( "p4" );
        addDependency( dependencyRight, dependencyBottom );

        IJavaProject result = finder.findFor( asList( dependencyLeft, dependencyBottom ) );
        assertThat( result, either( is( uberRoot ) ).or( is( project ) ) );
    }

    @Test
    public void unrelatedProjectsFindNothing() throws Exception {
        // [p1,p2], ?p1,p2 => []
        IJavaProject project = addProject( "p1" );
        IJavaProject unrelatedProject = addProject( "p2" );
        assertThat( finder.findFor( asList( project, unrelatedProject ) ), is( nullValue() ) );
    }

    @Test
    public void disconnectedDependencyGraphsFindNothing() throws Exception {
        // [p1->p2,p3], ?p2,p3 => []
        IJavaProject project = addProject( "p1" );
        IJavaProject dependency = addProject( "p2" );
        addDependency( project, dependency );
        IJavaProject unrelatedProject = addProject( "p3" );

        assertThat( finder.findFor( asList( dependency, unrelatedProject ) ), is( nullValue() ) );
    }
}
