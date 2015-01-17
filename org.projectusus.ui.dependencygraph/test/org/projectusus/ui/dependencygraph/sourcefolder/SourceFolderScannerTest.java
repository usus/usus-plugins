package org.projectusus.ui.dependencygraph.sourcefolder;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Test;

public class SourceFolderScannerTest {

    private IJavaProject project1;

    private IPackageFragmentRoot mainRoot;

    private IJavaProject project2;

    @Before
    public void setup() {
        project1 = mock( IJavaProject.class );
        when( project1.getPath() ).thenReturn( new Path( "Bla" ) );

        mainRoot = mock( IPackageFragmentRoot.class );
        IPath mainPath = new Path( "Bla/src/main/java" );
        when( mainRoot.getPath() ).thenReturn( mainPath );

        project2 = mock( IJavaProject.class );
        when( project2.getPath() ).thenReturn( new Path( "Foo" ) );
    }

    @SuppressWarnings( "unchecked" )
    @Test
    public void scanSingleProject() throws Exception {
        IPackageFragmentRoot[] roots = new IPackageFragmentRoot[] { mainRoot };
        when( project1.getPackageFragmentRoots() ).thenReturn( roots );

        SourceFolderScanner sourceFolderScanner = new SourceFolderScanner();

        Set<IPath> sourceFolders = sourceFolderScanner.scan( project1 );

        assertThat( sourceFolders, hasItems( path( "src/main/java" ) ) );
    }

    @SuppressWarnings( "unchecked" )
    @Test
    public void scanSingleProjectWithMultipleFragmentRoots() throws Exception {
        IPackageFragmentRoot testRoot = mock( IPackageFragmentRoot.class );
        IPath testPath = new Path( "Bla/src/test/java" );
        when( testRoot.getPath() ).thenReturn( testPath );

        IPackageFragmentRoot[] roots = new IPackageFragmentRoot[] { mainRoot, testRoot };
        when( project1.getPackageFragmentRoots() ).thenReturn( roots );

        SourceFolderScanner sourceFolderScanner = new SourceFolderScanner();

        Set<IPath> sourceFolders = sourceFolderScanner.scan( project1 );

        assertThat( sourceFolders, hasItems( path( "src/main/java" ), path( "src/test/java" ) ) );
    }

    @SuppressWarnings( "unchecked" )
    @Test
    public void scanMultipleProjects() throws Exception {
        IPackageFragmentRoot[] roots1 = new IPackageFragmentRoot[] { mainRoot };
        when( project1.getPackageFragmentRoots() ).thenReturn( roots1 );

        IPackageFragmentRoot testRoot = mock( IPackageFragmentRoot.class );
        IPath testPath = new Path( "Foo/src/test/java" );
        when( testRoot.getPath() ).thenReturn( testPath );

        IPackageFragmentRoot[] roots2 = new IPackageFragmentRoot[] { testRoot };
        when( project2.getPackageFragmentRoots() ).thenReturn( roots2 );

        SourceFolderScanner sourceFolderScanner = new SourceFolderScanner();

        Set<IPath> sourceFolders = sourceFolderScanner.scan( project1, project2 );
        assertThat( sourceFolders, hasItems( path( "src/main/java" ), path( "src/test/java" ) ) );
    }

    @Test
    public void testSetWithIPaths() throws Exception {
        IPackageFragmentRoot secondMainRoot = mock( IPackageFragmentRoot.class );
        IPath testPath = new Path( "Bla/src/main/java" );
        when( secondMainRoot.getPath() ).thenReturn( testPath );

        IPackageFragmentRoot[] roots1 = new IPackageFragmentRoot[] { mainRoot, secondMainRoot };
        when( project1.getPackageFragmentRoots() ).thenReturn( roots1 );

        SourceFolderScanner sourceFolderScanner = new SourceFolderScanner();

        Set<IPath> sourceFolders = sourceFolderScanner.scan( project1 );
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

}
