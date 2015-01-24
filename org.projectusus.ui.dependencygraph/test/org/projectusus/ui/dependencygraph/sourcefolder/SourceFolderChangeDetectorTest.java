package org.projectusus.ui.dependencygraph.sourcefolder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.IJavaProject;
import org.junit.Test;
import org.mockito.Mockito;
import org.projectusus.core.project2.IUSUSProject;

public class SourceFolderChangeDetectorTest {

    @Test
    public void detectSourceFolderAddition() {
        IJavaElement model = javaElement( IJavaElement.JAVA_MODEL );
        IJavaElementDelta modelDelta = javaElementDeltaFor( model );

        IJavaElement project = javaProject();
        IJavaElementDelta projectDelta = javaElementDeltaFor( project );
        Mockito.when( modelDelta.getChangedChildren() ).thenReturn( new IJavaElementDelta[] { projectDelta } );

        IJavaElement fragmentRoot = javaElement( IJavaElement.PACKAGE_FRAGMENT_ROOT );
        IJavaElementDelta fragmentDelta = javaElementDeltaFor( fragmentRoot );

        Mockito.when( projectDelta.getAddedChildren() ).thenReturn( new IJavaElementDelta[] { fragmentDelta } );
        Mockito.when( projectDelta.getChangedChildren() ).thenReturn( new IJavaElementDelta[0] );
        Mockito.when( projectDelta.getRemovedChildren() ).thenReturn( new IJavaElementDelta[0] );

        SourceFolderChangeDetector detector = new SourceFolderChangeDetector();
        assertThat( detector.analyze( modelDelta ), is( true ) );
    }

    @Test
    public void detectSourceFolderRemoval() {
        IJavaElement model = javaElement( IJavaElement.JAVA_MODEL );
        IJavaElementDelta modelDelta = javaElementDeltaFor( model );

        IJavaElement project = javaProject();
        IJavaElementDelta projectDelta = javaElementDeltaFor( project );
        Mockito.when( modelDelta.getChangedChildren() ).thenReturn( new IJavaElementDelta[] { projectDelta } );

        IJavaElement fragmentRoot = javaElement( IJavaElement.PACKAGE_FRAGMENT_ROOT );
        IJavaElementDelta fragmentDelta = javaElementDeltaFor( fragmentRoot );

        Mockito.when( projectDelta.getAddedChildren() ).thenReturn( new IJavaElementDelta[0] );
        Mockito.when( projectDelta.getChangedChildren() ).thenReturn( new IJavaElementDelta[0] );
        Mockito.when( projectDelta.getRemovedChildren() ).thenReturn( new IJavaElementDelta[] { fragmentDelta } );

        SourceFolderChangeDetector detector = new SourceFolderChangeDetector();
        assertThat( detector.analyze( modelDelta ), is( true ) );
    }

    @Test
    public void ignoreSourceFolderChangeAsRenameIsRemovalPlusAddition() {
        IJavaElement model = javaElement( IJavaElement.JAVA_MODEL );
        IJavaElementDelta modelDelta = javaElementDeltaFor( model );

        IJavaElement project = javaProject();
        IJavaElementDelta projectDelta = javaElementDeltaFor( project );
        Mockito.when( modelDelta.getChangedChildren() ).thenReturn( new IJavaElementDelta[] { projectDelta } );

        IJavaElement fragmentRoot = javaElement( IJavaElement.PACKAGE_FRAGMENT_ROOT );
        IJavaElementDelta fragmentDelta = javaElementDeltaFor( fragmentRoot );

        Mockito.when( projectDelta.getAddedChildren() ).thenReturn( new IJavaElementDelta[0] );
        Mockito.when( projectDelta.getChangedChildren() ).thenReturn( new IJavaElementDelta[] { fragmentDelta } );
        Mockito.when( projectDelta.getRemovedChildren() ).thenReturn( new IJavaElementDelta[0] );

        SourceFolderChangeDetector detector = new SourceFolderChangeDetector();
        assertThat( detector.analyze( modelDelta ), is( false ) );
    }

    private static IJavaElement javaElement( int elementType ) {
        IJavaElement element = Mockito.mock( IJavaElement.class );
        Mockito.when( element.getElementType() ).thenReturn( elementType );
        return element;
    }

    private static IJavaElement javaProject() {
        IJavaProject element = Mockito.mock( IJavaProject.class );
        Mockito.when( element.getElementType() ).thenReturn( IJavaElement.JAVA_PROJECT );
        IProject project = Mockito.mock( IProject.class );
        Mockito.when( element.getProject() ).thenReturn( project );
        Mockito.when( project.isAccessible() ).thenReturn( true );
        Mockito.when( project.getAdapter( IUSUSProject.class ) ).thenReturn( new DummyUsusProject() );
        return element;
    }

    private static IJavaElementDelta javaElementDeltaFor( IJavaElement project2 ) {
        IJavaElementDelta delta = Mockito.mock( IJavaElementDelta.class );
        Mockito.when( delta.getElement() ).thenReturn( project2 );
        return delta;
    }

    static class DummyUsusProject implements IUSUSProject {

        public boolean isUsusProject() {
            return true;
        }

        public void setUsusProject( boolean ususProject ) { //
        }

        public String getProjectName() {
            return "Dummy Project";
        }

    }
}
