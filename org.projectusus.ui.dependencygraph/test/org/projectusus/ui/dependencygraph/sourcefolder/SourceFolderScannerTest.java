package org.projectusus.ui.dependencygraph.sourcefolder;

import static org.eclipse.jdt.core.IPackageFragmentRoot.K_BINARY;
import static org.eclipse.jdt.core.IPackageFragmentRoot.K_SOURCE;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;

public class SourceFolderScannerTest {

    private final SourceFolderScanner sourceFolderScanner = new SourceFolderScanner();

    @Test
    public void scanSingleProject() throws Exception {
        IPackageFragmentRoot root = packageFragmentRoot( new Path( "Bla/src/main/java" ), K_SOURCE );
        IJavaProject project = javaProject( new Path( "Bla" ), root );

        Set<IPath> sourceFolders = sourceFolderScanner.scan( project );

        assertThat( sourceFolders, contains( path( "src/main/java" ) ) );
    }

    @Test
    public void scanEmptyProject() throws Exception {
        IJavaProject project = javaProject( new Path( "Bla" ) );

        Set<IPath> sourceFolders = sourceFolderScanner.scan( project );

        assertThat( sourceFolders, is( empty() ) );
    }

    @SuppressWarnings( "unchecked" )
    @Test
    public void scanSingleProjectWithMultipleFragmentRoots() throws Exception {
        IPackageFragmentRoot mainRoot = packageFragmentRoot( new Path( "Bla/src/main/java" ), K_SOURCE );
        IPackageFragmentRoot testRoot = packageFragmentRoot( new Path( "Bla/src/test/java" ), K_SOURCE );
        IJavaProject project = javaProject( new Path( "Bla" ), mainRoot, testRoot );

        Set<IPath> sourceFolders = sourceFolderScanner.scan( project );

        assertThat( sourceFolders, contains( path( "src/main/java" ), path( "src/test/java" ) ) );
    }

    @SuppressWarnings( "unchecked" )
    @Test
    public void scanMultipleProjects() throws Exception {
        IPackageFragmentRoot mainRoot = packageFragmentRoot( new Path( "Bla/src/main/java" ), K_SOURCE );
        IJavaProject project1 = javaProject( new Path( "Bla" ), mainRoot );

        IPackageFragmentRoot testRoot = packageFragmentRoot( new Path( "Foo/src/test/java" ), K_SOURCE );
        IJavaProject project2 = javaProject( new Path( "Foo" ), testRoot );

        Set<IPath> sourceFolders = sourceFolderScanner.scan( project1, project2 );

        assertThat( sourceFolders, contains( path( "src/main/java" ), path( "src/test/java" ) ) );
    }

    @Test
    public void testSetWithIPaths() throws Exception {
        IPackageFragmentRoot mainRoot = packageFragmentRoot( new Path( "Bla/src/main/java" ), K_SOURCE );
        IPackageFragmentRoot secondMainRoot = packageFragmentRoot( new Path( "Bla/src/main/java" ), K_SOURCE );
        IJavaProject project = javaProject( new Path( "Bla" ), mainRoot, secondMainRoot );

        Set<IPath> sourceFolders = sourceFolderScanner.scan( project );

        assertThat( sourceFolders, contains( path( "src/main/java" ) ) );
    }

    @Test
    public void ignoresBinaryPackageFragmentRoots() throws Exception {
        IPackageFragmentRoot mainRoot = packageFragmentRoot( new Path( "Bla/src/main/java" ), K_SOURCE );
        IPackageFragmentRoot secondMainRoot = packageFragmentRoot( new Path( "Bla/lib/some.jar" ), K_BINARY );
        IJavaProject project = javaProject( new Path( "Bla" ), mainRoot, secondMainRoot );

        Set<IPath> sourceFolders = sourceFolderScanner.scan( project );

        assertThat( sourceFolders, contains( path( "src/main/java" ) ) );
    }

    private Matcher<IPath> path( final String pathAsString ) {
        return new TypeSafeMatcher<IPath>() {

            public void describeTo( Description description ) {
                description.appendText( "path " + pathAsString );
            }

            @Override
            protected boolean matchesSafely( IPath path ) {
                return pathAsString.equals( path.toPortableString() );
            }
        };
    }

    private static IJavaProject javaProject( Path path, IPackageFragmentRoot... roots ) throws Exception {
        IJavaProject javaProject = mock( IJavaProject.class );
        when( javaProject.getPath() ).thenReturn( path );
        when( javaProject.getPackageFragmentRoots() ).thenReturn( roots );
        return javaProject;
    }

    private static IPackageFragmentRoot packageFragmentRoot( IPath path, int kind ) throws JavaModelException {
        IPackageFragmentRoot packageFragmentRoot = mock( IPackageFragmentRoot.class );
        when( packageFragmentRoot.getPath() ).thenReturn( path );
        when( Integer.valueOf( packageFragmentRoot.getKind() ) ).thenReturn( Integer.valueOf( kind ) );
        return packageFragmentRoot;
    }

}
