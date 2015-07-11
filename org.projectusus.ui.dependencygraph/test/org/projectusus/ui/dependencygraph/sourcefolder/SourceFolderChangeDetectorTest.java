package org.projectusus.ui.dependencygraph.sourcefolder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

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
        when( modelDelta.getChangedChildren() ).thenReturn( new IJavaElementDelta[] { projectDelta } );

        IJavaElement fragmentRoot = javaElement( IJavaElement.PACKAGE_FRAGMENT_ROOT );
        IJavaElementDelta fragmentDelta = javaElementDeltaFor( fragmentRoot );

        when( projectDelta.getAddedChildren() ).thenReturn( new IJavaElementDelta[] { fragmentDelta } );
        when( projectDelta.getChangedChildren() ).thenReturn( new IJavaElementDelta[0] );
        when( projectDelta.getRemovedChildren() ).thenReturn( new IJavaElementDelta[0] );

        SourceFolderChangeDetector detector = new SourceFolderChangeDetector();
        assertTrue( detector.analyze( modelDelta ) );
    }

    @Test
    public void detectSourceFolderRemoval() {
        IJavaElement model = javaElement( IJavaElement.JAVA_MODEL );
        IJavaElementDelta modelDelta = javaElementDeltaFor( model );

        IJavaElement project = javaProject();
        IJavaElementDelta projectDelta = javaElementDeltaFor( project );
        when( modelDelta.getChangedChildren() ).thenReturn( new IJavaElementDelta[] { projectDelta } );

        IJavaElement fragmentRoot = javaElement( IJavaElement.PACKAGE_FRAGMENT_ROOT );
        IJavaElementDelta fragmentDelta = javaElementDeltaFor( fragmentRoot );

        when( projectDelta.getAddedChildren() ).thenReturn( new IJavaElementDelta[0] );
        when( projectDelta.getChangedChildren() ).thenReturn( new IJavaElementDelta[0] );
        when( projectDelta.getRemovedChildren() ).thenReturn( new IJavaElementDelta[] { fragmentDelta } );

        SourceFolderChangeDetector detector = new SourceFolderChangeDetector();
        assertTrue( detector.analyze( modelDelta ) );
    }

    @Test
    public void ignoreSourceFolderChangeAsRenameIsRemovalPlusAddition() {
        IJavaElement model = javaElement( IJavaElement.JAVA_MODEL );
        IJavaElementDelta modelDelta = javaElementDeltaFor( model );

        IJavaElement project = javaProject();
        IJavaElementDelta projectDelta = javaElementDeltaFor( project );
        when( modelDelta.getChangedChildren() ).thenReturn( new IJavaElementDelta[] { projectDelta } );

        IJavaElement fragmentRoot = javaElement( IJavaElement.PACKAGE_FRAGMENT_ROOT );
        IJavaElementDelta fragmentDelta = javaElementDeltaFor( fragmentRoot );

        when( projectDelta.getAddedChildren() ).thenReturn( new IJavaElementDelta[0] );
        when( projectDelta.getChangedChildren() ).thenReturn( new IJavaElementDelta[] { fragmentDelta } );
        when( projectDelta.getRemovedChildren() ).thenReturn( new IJavaElementDelta[0] );

        SourceFolderChangeDetector detector = new SourceFolderChangeDetector();
        assertFalse( detector.analyze( modelDelta ) );
    }

    private static IJavaElement javaElement( int elementType ) {
        IJavaElement element = Mockito.mock( IJavaElement.class );
        intWhen( element.getElementType(), elementType );
        return element;
    }

    private static IJavaElement javaProject() {
        IJavaProject element = Mockito.mock( IJavaProject.class );
        intWhen( element.getElementType(), IJavaElement.JAVA_PROJECT );
        IProject project = Mockito.mock( IProject.class );
        when( element.getProject() ).thenReturn( project );
        booleanWhen( project.isAccessible(), true );
        when( project.getAdapter( IUSUSProject.class ) ).thenReturn( new DummyUsusProject() );
        return element;
    }

    private static IJavaElementDelta javaElementDeltaFor( IJavaElement project2 ) {
        IJavaElementDelta delta = Mockito.mock( IJavaElementDelta.class );
        when( delta.getElement() ).thenReturn( project2 );
        return delta;
    }

    private static void intWhen( int methodCall, int value ) {
        when( Integer.valueOf( methodCall ) ).thenReturn( Integer.valueOf( value ) );
    }

    private static void booleanWhen( boolean methodCall, boolean value ) {
        when( Boolean.valueOf( methodCall ) ).thenReturn( Boolean.valueOf( value ) );
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
